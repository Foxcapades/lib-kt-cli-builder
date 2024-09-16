package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.CliSerializationException
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.impl.*
import io.foxcapades.lib.cli.builder.arg.unsafeCast
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.flag.ref.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.impl.*
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.flag.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.filter
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.then
import io.foxcapades.lib.cli.builder.util.values.ValueAccessorKF1
import io.foxcapades.lib.cli.builder.util.values.ValueAccessorKP1
import io.foxcapades.lib.cli.builder.util.values.WrapperAccessorK1
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

internal class ReflectiveComponentResolver<T : Any>(
  parent:   ResolvedCommand<T>,
  instance: T,
  config:   CliSerializationConfig,
)
  : AbstractComponentResolver<T>(parent, config)
{
  override val type = instance::class

  private val typeInfo = ClassInfo(type)

  private val stream = filterRelevantMembers().filter { it.isUsable }

  private val instance = instance

  // region Public API

  override fun hasNext() = stream.hasNext()

  override fun next() = stream.next()

  // endregion Public API

  // region General Filtering

  private inline val ResolvedComponent.isUsable get() =
    when (this) {
      is ResolvedFlag<*> -> {
        validateFlagNames(config)
        isUsable()
      }

      is ResolvedArgument<*> -> isUsable()

      else -> false
    }

  private fun ResolvedFlag<*>.isUsable() =
    if (isRequired) {
      // TODO: interplay: flag vs argument requirements
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw CliSerializationException("$qualifiedName is marked as required but is not set")
    } else {
      forceAny().let { it.shouldSerialize(config, it.valueSource) }
    }

  private fun ResolvedArgument<*>.isUsable() =
    if (isRequired) {
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw CliSerializationException("$qualifiedName is marked as required but is not set")
    } else {
      forceAny().let { it.shouldSerialize(config, it.valueSource) }
    }

  private fun filterRelevantMembers() =
    typeInfo.mapFilterMembers(::siftMember)

  // sift class members down to only those that can be resolved to components
  private fun siftMember(member: MemberInfo<*, KCallable<*>>): ResolvedComponent? =
    @Suppress("UNCHECKED_CAST")
    when (member.member) {
      is KProperty1<*, *> -> siftProperty(member as MemberInfo<T, KProperty1<T, Any?>>)
      is KFunction1<*, *> -> siftFunction(member as MemberInfo<T, KFunction1<T, Any?>>)
      else                -> null // TODO: LOG HERE
    }

  // sift class properties down to only those that can be resolved to components
  private fun siftProperty(info: MemberInfo<T, KProperty1<T, Any?>>): ResolvedComponent? {
    // If the property is delegated, use the delegate for component resolution
    info.member.asDelegateType<CliCallComponent>(instance)
      ?.then { return@siftProperty siftDelegateProperty(it, info) }

    // if the property is not delegated...

    // get any relevant annotations for the property
    val annotations = info.relevantAnnotations()

    // If there are no annotations, and it is not a delegate, then it isn't
    // something we care about.
    if (annotations.isEmpty)
      return null

    return when {
      annotations.hasFlagAnnotation ->
        FauxFlag(annotations.flag!!, parent, ValueAccessorKP1(info.member, instance))
          .also { it.validateFlagNames(config) }

      annotations.hasArgumentAnnotation ->
        FauxArgument(annotations.argument!!, parent, ValueAccessorKP1(info.member, instance))

      annotations.hasCommandAnnotation -> SUB_COMMAND()

      else -> BUG()
    }
  }

  private fun siftFunction(info: MemberInfo<T, KFunction1<T, Any?>>): ResolvedComponent? {
    val func = info.member

    // Fetch any relevant annotations on the function.
    // We do this before the getter check to provide a more useful error.
    val annotations = info.relevantAnnotations()

    // If the function is not a getter
    if (!func.isGetter) {
      // but it has an annotation, report an error
      if (!annotations.isEmpty) {
        // TODO: This should be a concrete error type
        throw CliSerializationException("${func.qualifiedName(type)} is not a getter, but is annotated as a CLI component")
      }

      // else, it has no annotation, it's just some random function we don't
      // care about.
      return null
    }

    // if the function IS a getter...

    // Check the return type
    val returns = func.determineValueType()

    // If the function is not annotated, but returns a ...
    if (annotations.isEmpty) {
      return when (returns) {
        ValueType.Flag -> func.unsafeCast<T, Flag<Any?>?>()(instance)
          ?.let { ValueFlag(parent, it, WrapperAccessorK1(it::get, func, instance)) }

        ValueType.Argument -> func.unsafeCast<T, Argument<Any?>?>()(instance)
          ?.let { ValueArgument(parent, it, WrapperAccessorK1(it::get, func, instance)) }

        ValueType.Command -> SUB_COMMAND()
        ValueType.AnnotatedCommand -> SUB_COMMAND()

        // It's a random getter func that we don't care about
        ValueType.Value -> null
      }
    }

    // The function is both a getter and is annotated.
    return when {

      // if it is annotated as a flag
      annotations.hasFlagAnnotation -> when (returns) {
        // but just returns a plain value, use a faux flag
        ValueType.Value -> FauxFlag(annotations.flag!!, parent, ValueAccessorKF1(func, instance))

        // and is also a flag instance, use a joined flag
        ValueType.Flag  -> func.unsafeCast<T, Flag<Any?>?>()(parent.instance)
          ?.let { AnnotatedValueFlag(annotations.flag!!, parent, it, WrapperAccessorK1(it::get, func, instance)) }

        // else it's annotated as a flag, but is actually an argument or command
        else -> throw CliSerializationException("${func.qualifiedName(type)} is annotated as being a Flag, but returns a value of type ${returns.name}")
      }

      // if it is annotated as an argument
      annotations.hasArgumentAnnotation -> when (returns) {
        // but just returns a plain value, use a faux argument
        ValueType.Value    -> FauxArgument(annotations.argument!!, parent, ValueAccessorKF1(func, instance))

        // and is also an argument instance, use a joined argument
        ValueType.Argument -> func.unsafeCast<T, Argument<Any?>?>()(parent.instance)
          ?.let { AnnotatedValueArgument(annotations.argument!!, parent, it, WrapperAccessorK1(it::get, func, instance)) }

        // else it's annotated as an argument but is actually a flag or command
        else -> throw CliSerializationException("${func.qualifiedName(type)} is annotated as being an Argument, but returns a value of type ${returns.name}")
      }

      // if it is annotated as a command
      annotations.hasCommandAnnotation -> SUB_COMMAND()

      // impossible condition
      else -> BUG()
    }
  }

  // endregion General Filtering

  // region Delegate Filtering

  private fun siftDelegateProperty(del: CliCallComponent, prop: MemberInfo<T, KProperty1<T, Any?>>): ResolvedComponent {
    return when (del) {
      is Flag<*>     -> siftDelegateFlag(del.unsafeCast(), prop)
      is Argument<*> -> siftDelegateArg(del.unsafeCast(), prop)
      else           -> TODO("custom CliCallComponent types are currently unsupported")
    }
  }

  private fun siftDelegateFlag(del: Flag<Any?>, info: MemberInfo<T, KProperty1<T, Any?>>): ResolvedComponent {
    val annotations = info.relevantAnnotations()

    when {
      annotations.hasArgumentAnnotation -> throw CliSerializationException("${info.member.qualifiedName(type)} is a flag delegate annotated with @${CliArgument::class.simpleName}")
      annotations.hasCommandAnnotation  -> throw CliSerializationException("${info.member.qualifiedName(type)} is a flag delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasFlagAnnotation)
      AnnotatedDelegateFlag(annotations.flag!!, parent, del, ValueAccessorKP1(info.member, instance))
        .also { it.validateFlagNames(config) }
    else
      DelegateFlag(parent, del, ValueAccessorKP1(info.member, instance))
        .also { it.validateFlagNames(config) }
  }

  private fun siftDelegateArg(del: Argument<Any?>, info: MemberInfo<T, KProperty1<T, Any?>>): ResolvedComponent {
    val annotations = info.relevantAnnotations()

    when {
      annotations.hasFlagAnnotation    -> throw CliSerializationException("${info.member.qualifiedName(type)} is an annotation delegate annotated with @${CliFlag::class.simpleName}")
      annotations.hasCommandAnnotation -> throw CliSerializationException("${info.member.qualifiedName(type)} is an annotation delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasArgumentAnnotation)
      AnnotatedDelegateArgument(annotations.argument!!, parent, del.forceAny(), ValueAccessorKP1(info.member, instance))
    else
      DirectArgument(parent, del.forceAny(), ValueAccessorKP1(info.member, instance))
  }

  private fun siftDelegateCom(del: Command, prop: KProperty1<T, Command?>): ResolvedComponent {
    return SUB_COMMAND()
  }

  // endregion Delegate Filtering
}
