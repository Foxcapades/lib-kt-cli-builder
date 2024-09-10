package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.impl.AnnotatedValueArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.UnlinkedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.ValueArgument
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.impl.AnnotatedValueFlag
import io.foxcapades.lib.cli.builder.flag.ref.impl.UnlinkedFlag
import io.foxcapades.lib.cli.builder.flag.ref.impl.ValueFlag
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.takeAs
import io.foxcapades.lib.cli.builder.util.then
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.jvm.isAccessible

internal class ArbitraryComponentResolver<T : Any>(
  config:   CliSerializationConfig,
  instance: ResolvedCommand<T>,
  iterable: Iterable<Any>
)
  : Iterator<ResolvedComponent>
{
  private val logger = LoggerFactory.getLogger(javaClass)!!

  private val config = config

  private val instance = instance

  private val stream = iterable.iterator()

  private val queue = ArrayDeque<Any>(4)

  override fun hasNext() = queue.isNotEmpty() || prepQueue()

  override fun next(): ResolvedComponent {
    return when (val ref = queue.first()) {
      // It's a serializable component
      is ResolvedComponent -> ref.also { queue.removeFirst() }

      // it's a component resolver instance
      is Iterator<*> -> (ref.next() as ResolvedComponent).also { if (!ref.hasNext()) queue.removeFirst() }

      else -> BUG()
    }
  }

  private fun prepQueue(): Boolean {
    while (queue.isEmpty() && stream.hasNext()) {
      when (val next = stream.next()) {
        // It's a property reference, need to determine what the property type
        // is.
        is KProperty1<*, *> -> tryProperty(next.forceAny())

        // It's a function reference, need to confirm that it's a getter and
        // determine its type.
        is KFunction1<*, *> -> tryGetter(next)

        // It's a flag instance with no parenting.
        is Flag<*, *> -> tryFlag(next)

        // It's an argument instance with no parenting.
        is Argument<*> -> tryArgument(next)

        // It's a command instance with no parenting.
        is Command -> tryCommand(next)

        // It's not a known type, treat it as a value.
        else -> tryValue(next)
      }
    }

    return false
  }

  // region Property Reference

  private fun tryProperty(prop: KProperty1<Any, Any?>) {
    prop.isAccessible = true

    // if the property is delegated to a component type
    prop.getDelegate(instance.instance)?.then {
      when (it) {
        is Flag<*, *>  -> return tryFlagDelegate(prop, it)
        is Argument<*> -> return tryArgumentDelegate(prop, it)
        // TODO: handle other possible delegate types?
        //       log if it's a cli component subtype?
      }
      // TODO: log that it's a delegate but not a known delegate type
    }

    // else, if the property represents a component type
    prop.returnType.classifier
      ?.takeAs<KClassifier, KClass<*>>()
      ?.then {
        when {
          Flag::class.isSuperclassOf(it)     -> return tryFlagPropertyValue(prop.unsafeCast(), it.unsafeCast())
          Argument::class.isSuperclassOf(it) -> return tryArgumentPropertyValue(prop.unsafeCast(), it.unsafeCast())
          Command::class.isSuperclassOf(it)  -> return tryCommandPropertyValue(prop.unsafeCast(), it.unsafeCast())
          // TODO: log if it's a cli component subtype
        }
      }

    // else, it's either annotated or a plain value
    return tryUnknownPropertyValue(prop)
  }

  private fun tryFlagDelegate(prop: KProperty1<Any, Any?>, delegate: Flag<*, *>) {}

  private fun tryArgumentDelegate(prop: KProperty1<Any, Any?>, delegate: Argument<*>) {}

  private fun tryFlagPropertyValue(prop: KProperty1<Any, Flag<*, *>?>, type: KClass<out Flag<*, *>>) {}

  private fun tryArgumentPropertyValue(prop: KProperty1<Any, Argument<*>?>, type: KClass<out Argument<*>>) {}

  private fun tryCommandPropertyValue(prop: KProperty1<Any, Command?>, type: KClass<out Command>) {}

  private fun tryUnknownPropertyValue(prop: KProperty1<Any, Any?>) {
    // TODO: check annotations!
    //       if it is not annotated, then it's a plain value
  }

  // endregion Property Reference


  // region Function Reference

  private fun tryGetter(getter: KFunction1<*, *>) {
    getter.isAccessible = true

    // ensure it's a getter!
    if (!getter.isGetter) {
      throw IllegalArgumentException("cannot use non-getter function ${getter.qualifiedName(instance.type)} as a cli component")
    }

    // else, if the property represents a component type
    getter.returnType.classifier
      ?.takeAs<KClassifier, KClass<*>>()
      ?.then {
        when {
          Flag::class.isSuperclassOf(it)     -> return tryFlagGetterValue(getter.unsafeCast())
          Argument::class.isSuperclassOf(it) -> return tryArgumentGetterValue(getter.unsafeCast())
          Command::class.isSuperclassOf(it)  -> return tryCommandGetterValue(getter.unsafeCast(), it.unsafeCast())
          // TODO: log if it's a cli component subtype
        }
      }

    // check return type for annotations
    // check annotations
    // else, plain value
  }

  private fun tryFlagGetterValue(getter: KFunction1<T, Flag<*, *>?>) {
    val annotations = try {
      RelevantAnnotations.of(getter.relevantAnnotations)
    } catch (e: RelevantAnnotations.DuplicateException) {
      throw getter.makeDuplicateAnnotationsError(instance::class, e.annotation::class)
    }

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is a Flag getter annotated with ${annotations.command!!::class}")

    if (annotations.hasArgumentAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is a Flag getter annotated with ${annotations.argument!!::class}")

    val flag = (getter(instance.instance) ?: return).forceAny()

    if (annotations.hasFlagAnnotation)
      queue.addLast(AnnotatedValueFlag(annotations.flag!!, instance, flag, getter.unsafeCast<T, Flag<Argument<Any?>, Any?>>()))
    else
      queue.addLast(ValueFlag(instance, flag, getter.unsafeCast<T, Flag<Argument<Any?>, Any?>>()))
  }

  private fun tryArgumentGetterValue(getter: KFunction1<Any, Argument<*>?>) {
    val annotations = try {
      RelevantAnnotations.of(getter.relevantAnnotations)
    } catch (e: RelevantAnnotations.DuplicateException) {
      throw getter.makeDuplicateAnnotationsError(instance::class, e.annotation::class)
    }

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is an Argument getter annotated with ${annotations.command!!::class}")

    if (annotations.hasFlagAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is an Argument getter annotated with ${annotations.flag!!::class}")

    val arg = (getter(instance.instance) ?: return).forceAny()

    if (annotations.hasArgumentAnnotation)
      queue.addLast(AnnotatedValueArgument(annotations.argument!!, instance, arg, getter.unsafeCast<T, Argument<Any?>>()))
    else
      queue.addLast(ValueArgument(instance, arg, getter.unsafeCast<T, Argument<Any?>>()))
  }

  private fun tryCommandGetterValue(getter: KFunction1<Any, Command?>, type: KClass<out Command>) {
    val annotations = try {
      RelevantAnnotations.of(getter.relevantAnnotations)
    } catch (e: RelevantAnnotations.DuplicateException) {
      throw getter.makeDuplicateAnnotationsError(instance::class, e.annotation::class)
    }

    if (annotations.hasArgumentAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is a Command getter annotated with ${annotations.argument!!::class}")

    if (annotations.hasFlagAnnotation)
      throw IllegalStateException("${getter.qualifiedName(instance::class)} is a Command getter annotated with ${annotations.flag!!::class}")

    val com = (getter(instance.instance) ?: return)

    if (annotations.hasCommandAnnotation)
      queue.addLast(AnnotatedValueArgument(annotations.argument!!, instance, arg, getter.unsafeCast()))
    else
      queue.addLast(ValueArgument(instance, arg, getter.unsafeCast()))
  }

  // endregion Function Reference

  private fun tryFlag(flag: Flag<*, *>) =
    queue.addLast(UnlinkedFlag(instance, flag.forceAny()).apply { validateFlagNames(config) })

  private fun tryArgument(argument: Argument<*>) =
    queue.addLast(UnlinkedArgument(instance, argument.forceAny()))

  private fun tryCommand(command: Command) {
    // unlinked subcommand, queue it and a sub component resolver instance
  }

  private fun tryValue(value: Any?) {
    // some unknown value
  }
}
