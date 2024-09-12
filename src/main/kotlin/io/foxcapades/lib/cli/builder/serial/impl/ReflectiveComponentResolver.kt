package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.impl.*
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.component.tryAsValueAccessor
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.flag.ref.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.impl.*
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.values.ValueAccessorKP1
import io.foxcapades.lib.cli.builder.util.filter
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.then
import kotlin.reflect.*

internal class ReflectiveComponentResolver<T : Any>(
  parent: ResolvedCommand<T>,
  config: CliSerializationConfig,
)
  : AbstractComponentResolver<T>(parent, config)
{
  private val typeInfo = ClassInfo(type)

  override fun hasNext(): Boolean {
    TODO("Not yet implemented")
  }

  override fun next(): ResolvedComponent {
    TODO("Not yet implemented")
  }

  private fun filterUsableMembers() = filterRelevantMembers().filter { it.isUsable }

  private inline val ResolvedComponent.isUsable get() =
    when (this) {
      is ResolvedFlag<*>     -> isUsable()
      is ResolvedArgument<*> -> isUsable()
      else                   -> false
    }

  private fun ResolvedFlag<*>.isUsable() =
    if (isRequired) {
      // TODO: interplay: flag vs argument requirements
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw IllegalStateException("$qualifiedName is marked as required but is not set")
    } else {
      forceAny().let { it.shouldSerialize(config, it.tryAsValueAccessor<T, Any?>()) }
    }

  private fun ResolvedArgument<*>.isUsable() =
    if (isRequired) {
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw IllegalStateException("$qualifiedName is marked as required but is not set")
    } else {
      forceAny().let { it.shouldSerialize(config, it.tryAsValueAccessor<T, Any?>()) }
    }

  private fun filterRelevantMembers() =
    typeInfo.mapFilterMembers(::siftMember) { p, _ -> p }

  private fun siftMember(member: KCallable<*>): ResolvedComponent? =
    when (member) {
      is KProperty1<*, *> -> siftProperty(member.unsafeCast())
      is KFunction1<*, *> -> siftFunction(member.forceAny())
      else                -> null // TODO: LOG HERE
    }

  private fun siftProperty(prop: KProperty1<T, Any?>): ResolvedComponent? {
    prop.asDelegateType<CliCallComponent>(parent)
      ?.then { return siftDelegateProperty(it, prop) }

    val annotations = prop.relevantAnnotations()

    if (annotations.isEmpty)
      return null

    return when {
      annotations.hasFlagAnnotation ->
        FauxFlag(annotations.flag!!, parent, ValueAccessorKP1(prop, parent.instance))
          .also { it.validateFlagNames(config) }

      annotations.hasArgumentAnnotation ->
        FauxArgument(annotations.argument!!, parent, ValueAccessorKP1(prop, parent.instance))

      annotations.hasCommandAnnotation -> TODO("subcommands are not yet supported")

      else -> BUG()
    }
  }

  // region Delegates

  private fun siftDelegateProperty(del: CliCallComponent, prop: KProperty1<T, *>): ResolvedComponent {
    return when (del) {
      is Flag<*>     -> siftDelegateFlag(del, prop)
      is Argument<*> -> siftDelegateArg(del, prop)
      is Command     -> siftDelegateCom(del, prop)
      else           -> TODO("custom CliCallComponent types are currently unsupported")
    }
  }

  private fun siftDelegateFlag(del: Flag<*>, prop: KProperty1<T, *>): ResolvedComponent {
    val annotations = prop.relevantAnnotations()

    when {
      annotations.hasArgumentAnnotation -> throw IllegalStateException("${prop.qualifiedName(type)} is a flag delegate annotated with @${CliArgument::class.simpleName}")
      annotations.hasCommandAnnotation  -> throw IllegalStateException("${prop.qualifiedName(type)} is a flag delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasFlagAnnotation)
      AnnotatedDelegateFlag(annotations.flag!!, parent, del.forceAny(), ValueAccessorKP1(prop, parent.instance))
        .also { it.validateFlagNames(config) }
    else
      DelegateFlag(parent, del.forceAny(), ValueAccessorKP1(prop, parent.instance))
  }

  private fun siftDelegateArg(del: Argument<Any?>, prop: KProperty1<T, Any?>): ResolvedComponent {
    val annotations = prop.relevantAnnotations()

    when {
      annotations.hasFlagAnnotation    -> throw IllegalStateException("${prop.qualifiedName(type)} is an annotation delegate annotated with @${CliFlag::class.simpleName}")
      annotations.hasCommandAnnotation -> throw IllegalStateException("${prop.qualifiedName(type)} is an annotation delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasArgumentAnnotation)
      AnnotatedDelegateArgument(annotations.argument!!, parent, del.forceAny(), ValueAccessorKP1(prop, parent.instance))
    else
      DirectArgument(parent, del.forceAny(), ValueAccessorKP1(prop, parent.instance))
  }

  private fun siftDelegateCom(del: Command, prop: KProperty1<T, *>): ResolvedComponent {
    return SUB_COMMAND()
  }

  // endregion Delegates

  private fun siftFunction(func: KFunction1<T, Any?>): ResolvedComponent? {
    val annotations = func.relevantAnnotations()

    if (!func.isGetter) {
      if (!annotations.isEmpty) {
        // TODO: This should be a concrete error type
        throw IllegalStateException("${func.qualifiedName(type)} is not a getter, but is annotated as a CLI component")
      }

      return null
    }

    // If there is a relevant return type.
    val returns = func.determineValueType()

    if (annotations.isEmpty) {
      // it's not annotated, but it might be a value getter
      return when (returns) {
        ValueType.Flag -> func.unsafeCast<T, Flag<Any?>?>()(parent.instance)
          ?.let { ValueFlag(parent, it, func.unsafeCast<T, Flag<Any?>>()) }

        ValueType.Argument -> func.unsafeCast<T, Argument<Any?>?>()(parent.instance)
          ?.let { ValueArgument(type, parent, it, func.unsafeCast<T, Argument<Any?>>()) }

        ValueType.Command -> TODO("subcommands are not currently supported")
        ValueType.AnnotatedCommand -> TODO("subcommands are not currently supported")

        ValueType.Value -> null
      }
    }

    if (annotations.count > 1) {
      throw IllegalStateException("${func.qualifiedName(type)} is annotated as being multiple different types of CLI component")
    }

    return when {
      annotations.hasFlagAnnotation -> when (returns) {
        ValueType.Value -> FauxFlag(annotations.flag!!, parent, func)
        ValueType.Flag  -> func.unsafeCast<T, Flag<Any?>?>()(parent.instance)
          ?.let { AnnotatedValueFlag(annotations.flag!!, parent, it, func.unsafeCast<T, Flag<Any?>>()) }

        else -> throw IllegalStateException("${func.qualifiedName(type)} is annotated as being a Flag, but returns a value of type ${returns.name}")
      }

      annotations.hasArgumentAnnotation -> when (returns) {
        ValueType.Value    -> FauxArgument(annotations.argument!!, parent.instance,
          parent, func)
        ValueType.Argument -> func.unsafeCast<T, Argument<Any?>?>()(parent.instance)
          ?.let { AnnotatedValueArgument(annotations.argument!!, parent, it, func.unsafeCast<T, Argument<Any?>>()) }

        else -> throw IllegalStateException("${func.qualifiedName(type)} is annotated as being an Argument, but returns a value of type ${returns.name}")
      }

      annotations.hasCommandAnnotation -> {
        TODO("subcommands are not currently supported")
      }

      else -> null
    }
  }
}
