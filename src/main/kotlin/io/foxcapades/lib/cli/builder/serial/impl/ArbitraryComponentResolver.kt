package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.impl.UnlinkedArgument
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.impl.UnlinkedFlag
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.reflect.forceAny
import io.foxcapades.lib.cli.builder.util.reflect.isGetter
import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import io.foxcapades.lib.cli.builder.util.reflect.unsafeCast
import io.foxcapades.lib.cli.builder.util.takeAs
import io.foxcapades.lib.cli.builder.util.then
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.jvm.isAccessible

internal class ArbitraryComponentResolver(
  config:   CliSerializationConfig,
  instance: ResolvedCommand<*>,
  iterable: Iterable<Any>
)
  : Iterator<ResolvedComponent>
{
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
          Flag::class.isSuperclassOf(it)     -> return tryFlagGetterValue(getter.unsafeCast(), it.unsafeCast())
          Argument::class.isSuperclassOf(it) -> return tryArgumentGetterValue(getter.unsafeCast(), it.unsafeCast())
          Command::class.isSuperclassOf(it)  -> return tryCommandGetterValue(getter.unsafeCast(), it.unsafeCast())
          // TODO: log if it's a cli component subtype
        }
      }

    // check return type & annotations
    // check annotations
    // else, plain value
  }

  private fun tryFlagGetterValue(prop: KFunction1<Any, Flag<*, *>?>, type: KClass<out Flag<*, *>>) {
    // check annotations
  }

  private fun tryArgumentGetterValue(prop: KFunction1<Any, Argument<*>?>, type: KClass<out Argument<*>>) {
    // check annotations
  }

  private fun tryCommandGetterValue(prop: KFunction1<Any, Command?>, type: KClass<out Command>) {
    // check annotations
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
