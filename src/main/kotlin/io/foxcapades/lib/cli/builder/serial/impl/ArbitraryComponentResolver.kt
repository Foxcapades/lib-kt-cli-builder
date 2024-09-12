package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.*
import io.foxcapades.lib.cli.builder.arg.unsafeCast
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.command.ref.forceAny
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.flag.ref.impl.*
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.flag.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import io.foxcapades.lib.cli.builder.util.reflect.safeName
import io.foxcapades.lib.cli.builder.util.reflect.unsafeCast
import io.foxcapades.lib.cli.builder.util.then
import io.foxcapades.lib.cli.builder.util.values.AnonymousComponentValue
import io.foxcapades.lib.cli.builder.util.values.ValueAccessorKF0
import io.foxcapades.lib.cli.builder.util.values.ValueAccessorKP0
import io.foxcapades.lib.cli.builder.util.values.WrapperAccessorK0
import kotlin.reflect.KClass
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty0
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

internal class ArbitraryComponentResolver<T : Any>(
  config:   CliSerializationConfig,
  instance: ResolvedCommand<T>,
  iterable: Iterable<Any>
)
  : AbstractComponentResolver<T>(instance, config)
{
  private val stream = iterable.iterator()

  private val queue = ArrayDeque<Any>(4)

  override val type: KClass<out T>
    get() = parent.type

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
        is KProperty0<*> -> tryProperty(next)

        // It's a function reference, need to confirm that it's a getter and
        // determine its type.
        is KFunction0<*> -> tryGetter(next)

        // It's a flag instance with no parenting.
        is Flag<*> -> tryFlag(next.unsafeCast())

        // It's an argument instance with no parenting.
        is Argument<*> -> tryArgument(next.unsafeCast())

        // It's a command instance with no parenting.
        is Command -> tryCommand(next)

        // It's not a known type, treat it as a value.
        else -> tryValue(next)
      }
    }

    return false
  }

  private fun enqueueFlag(flag: ResolvedFlag<*>) {
    flag.validateFlagNames(config)
    queue.addLast(flag)
  }

  // region Property Reference

  private fun tryProperty(prop: KProperty0<Any?>) {
    prop.isAccessible = true

    // if the property is delegated to a component type
    when (val it = prop.getDelegate()) {
      null -> {} // null means it wasn't a delegate

      is Flag<*> -> return tryFlagDelegate(prop, it.forceAny())

      is Argument<*> -> return tryArgumentDelegate(prop, it.forceAny())

      is CliCallComponent -> {
        logger.warn("ignoring unrecognized delegated property type: {}", it::class.qualifiedName)
      }

      else -> {
        logger.debug("treating delegate property as value: {}<{}>", prop.name, it::class.qualifiedName)
      }
    }

    return when (prop.determineValueType()) {
      ValueType.Flag             -> tryFlagPropertyValue(prop.unsafeCast())
      ValueType.Argument         -> tryArgumentPropertyValue(prop.unsafeCast())
      ValueType.Command          -> tryCommandPropertyValue(prop.unsafeCast())
      ValueType.AnnotatedCommand -> tryFauxCommandPropertyValue(prop.unsafeCast())
      ValueType.Value            -> tryUnknownPropertyValue(prop)
    }
  }

  private fun tryFlagDelegate(prop: KProperty0<Any?>, delegate: Flag<Any?>) {
    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return enqueueFlag(DelegateFlag(parent.forceAny(), delegate, ValueAccessorKP0(prop, type)))

    if (annotations.hasFlagAnnotation)
      return enqueueFlag(AnnotatedDelegateFlag(annotations.flag!!, parent.forceAny(), delegate, ValueAccessorKP0(prop, type)))

    if (annotations.hasArgumentAnnotation)
      throw IllegalStateException("Flag property \"${prop.name}\" provided by ${type.safeName} is annotated as an Argument")

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("Flag property \"${prop.name}\" provided by ${type.safeName} is annotated as a Command")

    BUG()
  }

  private fun tryArgumentDelegate(prop: KProperty0<Any?>, delegate: Argument<Any?>) {
    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return queue.addLast(DirectArgument(parent.forceAny(), delegate, ValueAccessorKP0(prop, type)))

    if (annotations.hasArgumentAnnotation)
      return queue.addLast(AnnotatedDelegateArgument(annotations.argument!!, parent.forceAny(), delegate, ValueAccessorKP0(prop, type)))

    if (annotations.hasFlagAnnotation)
      throw IllegalStateException("Argument property \"${prop.name}\" provided by ${type.safeName} is annotated as a Flag")

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("Argument property \"${prop.name}\" provided by ${type.safeName} is annotated as a Command")

    BUG()
  }

  private fun tryFlagPropertyValue(prop: KProperty0<Flag<Any?>?>) {
    val instance    = (prop.get() ?: return).forceAny()
    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return enqueueFlag(UnlinkedFlag(parent, instance, WrapperAccessorK0(instance::get, prop, type)))

    if (annotations.hasFlagAnnotation)
      return enqueueFlag(AnnotatedUnlinkedFlag(annotations.flag!!, parent, instance, WrapperAccessorK0(instance::get, prop, type)))

    if (annotations.hasArgumentAnnotation)
      throw IllegalStateException("Flag property \"${prop.name}\" provided by ${type.safeName} is annotated as an Argument")

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("Flag property \"${prop.name}\" provided by ${type.safeName} is annotated as a Command")

    BUG()
  }

  private fun tryArgumentPropertyValue(prop: KProperty0<Argument<Any?>?>) {
    val instance    = (prop.get() ?: return).forceAny()
    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return queue.addLast(UnlinkedArgument(parent, instance, WrapperAccessorK0(instance::get, prop, type)))

    if (annotations.hasArgumentAnnotation)
      return queue.addLast(AnnotatedUnlinkedArgument(annotations.argument!!, parent, instance, WrapperAccessorK0(instance::get, prop, type)))

    if (annotations.hasFlagAnnotation)
      throw IllegalStateException("Argument property \"${prop.name}\" provided by ${type.safeName} is annotated as a Flag")

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("Argument property \"${prop.name}\" provided by ${type.safeName} is annotated as a Command")

    BUG()
  }

  private fun tryCommandPropertyValue(prop: KProperty0<Command?>) = SUB_COMMAND<Unit>()

  private fun tryFauxCommandPropertyValue(prop: KProperty0<Any?>) = SUB_COMMAND<Unit>()

  private fun tryUnknownPropertyValue(prop: KProperty0<Any?>) {
    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return

    if (annotations.hasFlagAnnotation)
      return enqueueFlag(FauxFlag(annotations.flag!!, parent, ValueAccessorKP0(prop, null)))

    if (annotations.hasArgumentAnnotation)
      return queue.addLast(FauxArgument(annotations.argument!!, parent, ValueAccessorKP0(prop, null)))

    if (annotations.hasCommandAnnotation)
      return SUB_COMMAND()

    TODO("treat this as a headless positional argument")
  }

  // endregion Property Reference


  // region Function Reference

  private fun tryGetter(getter: KFunction0<Any?>) {
    getter.isAccessible = true

    when (getter.determineValueType()) {
      ValueType.Flag             -> tryFlagGetterValue(getter.unsafeCast())
      ValueType.Argument         -> tryArgumentGetterValue(getter.unsafeCast())
      ValueType.Command          -> tryCommandGetterValue(getter.unsafeCast())
      ValueType.AnnotatedCommand -> tryFauxCommandGetterValue(getter.unsafeCast())
      ValueType.Value            -> tryUnknownGetterValue(getter)
    }
  }

  private fun tryFlagGetterValue(getter: KFunction0<Flag<*>?>) {
    val annotations = getter.relevantAnnotations()

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("${getter.qualifiedName(parent::class)} is a Flag getter annotated with ${annotations.command!!::class}")

    if (annotations.hasArgumentAnnotation)
      throw IllegalStateException("${getter.qualifiedName(parent::class)} is a Flag getter annotated with ${annotations.argument!!::class}")

    val flag = (getter() ?: return).forceAny()

    if (annotations.hasFlagAnnotation)
      enqueueFlag(AnnotatedValueFlag(annotations.flag!!, parent, flag, ValueAccessorKF0(getter.unsafeCast<Flag<Any?>>(), type)))
    else
      enqueueFlag(ValueFlag(parent, flag, ValueAccessorKF0(getter.unsafeCast<Flag<Any?>>(), type)))
  }

  private fun tryArgumentGetterValue(getter: KFunction0<Argument<*>?>) {
    val annotations = getter.relevantAnnotations()

    if (annotations.hasCommandAnnotation)
      throw IllegalStateException("${getter.qualifiedName(parent::class)} is an Argument getter annotated with ${annotations.command!!::class}")

    if (annotations.hasFlagAnnotation)
      throw IllegalStateException("${getter.qualifiedName(parent::class)} is an Argument getter annotated with ${annotations.flag!!::class}")

    val arg = (getter() ?: return).forceAny()

    if (annotations.hasArgumentAnnotation)
      queue.addLast(AnnotatedValueArgument(annotations.argument!!, parent, arg, ValueAccessorKF0(getter.unsafeCast<Argument<Any?>>(), type)))
    else
      queue.addLast(DirectArgument(parent, arg, ValueAccessorKF0(getter.unsafeCast<Argument<Any?>>(), type)))
  }

  private fun tryCommandGetterValue(getter: KFunction0<Command?>) = SUB_COMMAND<Unit>()

  private fun tryFauxCommandGetterValue(getter: KFunction0<Any?>) = SUB_COMMAND<Unit>()

  private fun tryUnknownGetterValue(getter: KFunction0<Any?>) {
    val annotations = getter.relevantAnnotations()

    if (annotations.isEmpty)
      return

    if (annotations.hasFlagAnnotation)
      return enqueueFlag(FauxFlag(annotations.flag!!, parent, ValueAccessorKF0(getter, null)))

    if (annotations.hasArgumentAnnotation)
      return queue.addLast(FauxArgument(annotations.argument!!, parent, ValueAccessorKF0(getter, null)))

    if (annotations.hasCommandAnnotation)
      return SUB_COMMAND()

    queue.addLast(HeadlessArgument(parent, getter(), AnonymousComponentValue))
  }

  // endregion Function Reference

  private fun tryFlag(flag: Flag<Any?>) =
    if (flag is ResolvedFlag)
      enqueueFlag(flag)
    else
      enqueueFlag(UnlinkedFlag(parent, flag, AnonymousComponentValue).apply { validateFlagNames(config) })

  private fun tryArgument(argument: Argument<Any?>) =
    if (argument is ResolvedArgument)
      queue.addLast(argument)
    else
      queue.addLast(UnlinkedArgument(parent, argument, AnonymousComponentValue))

  private fun tryCommand(command: Command) = SUB_COMMAND<Unit>()

  private fun tryValue(value: Any?) {
    if (value != null)
      value::class.findAnnotation<CliCommand>()
        ?.then { return@tryValue SUB_COMMAND() }

    queue.addLast(HeadlessArgument(parent, value, AnonymousComponentValue))
  }
}
