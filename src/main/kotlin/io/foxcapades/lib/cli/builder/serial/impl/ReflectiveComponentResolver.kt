package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.forceAny
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.forceAny
import io.foxcapades.lib.cli.builder.arg.ref.impl.AnnotatedDelegateArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.FauxArgument
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.component.tryAsValueAccessor
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.forceAny
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.flag.ref.forceAny
import io.foxcapades.lib.cli.builder.flag.ref.impl.AnnotatedDelegateFlag
import io.foxcapades.lib.cli.builder.flag.ref.impl.DelegateFlag
import io.foxcapades.lib.cli.builder.flag.ref.impl.FauxFlag
import io.foxcapades.lib.cli.builder.flag.ref.validateFlagNames
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.filter
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.then
import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSuperclassOf

internal class ReflectiveComponentResolver<T : Any>(
  instance: ResolvedCommand<T>,
  config:   CliSerializationConfig,
)
  : Iterator<ResolvedComponent>
{
  private val instance = instance

  private val config = config

  private val type
    get() = instance.type

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
    prop.asDelegateType<CliCallComponent>(instance)
      ?.then { return siftDelegateProperty(it, prop) }

    val annotations = RelevantAnnotations.of(type, prop)

    if (annotations.isEmpty)
      return null

    if (annotations.count > 1)
      throw IllegalStateException("${prop.qualifiedName(type)} is annotated with more than one type of CLI annotation")

    return when {
      annotations.hasFlagAnnotation ->
        FauxFlag(annotations.flag!!, instance, prop)
          .also { it.validateFlagNames(config) }

      annotations.hasArgumentAnnotation ->
        FauxArgument(annotations.argument!!, instance.instance, instance, prop)

      annotations.hasCommandAnnotation -> TODO("subcommands are not yet supported")

      else -> BUG()
    }
  }

  // region Delegates

  private fun siftDelegateProperty(del: CliCallComponent, prop: KProperty1<T, *>): ResolvedComponent {
    return when (del) {
      is Flag<*, *>  -> siftDelegateFlag(del, prop)
      is Argument<*> -> siftDelegateArg(del, prop)
      is Command     -> siftDelegateCom(del, prop)
      else           -> TODO("custom CliCallComponent types are currently unsupported")
    }
  }

  private fun siftDelegateFlag(del: Flag<*, *>, prop: KProperty1<T, *>): ResolvedComponent {
    val annotations = RelevantAnnotations.of(type, prop)

    when {
      annotations.hasArgumentAnnotation -> throw IllegalStateException("${prop.qualifiedName(type)} is a flag delegate annotated with @${CliArgument::class.simpleName}")
      annotations.hasCommandAnnotation  -> throw IllegalStateException("${prop.qualifiedName(type)} is a flag delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasFlagAnnotation)
      AnnotatedDelegateFlag(annotations.flag!!, instance, del.forceAny(), prop.unsafeCast<T, Any?>())
        .also { it.validateFlagNames(config) }
    else
      DelegateFlag(instance, del.forceAny(), prop.unsafeCast<T, Any?>())
  }

  private fun siftDelegateArg(del: Argument<*>, prop: KProperty1<T, *>): ResolvedComponent {
    val annotations = RelevantAnnotations.of(type, prop)

    when {
      annotations.hasFlagAnnotation    -> throw IllegalStateException("${prop.qualifiedName(type)} is an annotation delegate annotated with @${CliFlag::class.simpleName}")
      annotations.hasCommandAnnotation -> throw IllegalStateException("${prop.qualifiedName(type)} is an annotation delegate annotated with @${CliCommand::class.simpleName}")
    }

    return if (annotations.hasArgumentAnnotation)
      AnnotatedDelegateArgument(annotations.argument!!, instance, del.forceAny(), prop.unsafeCast<T, Any?>())
    else
      DelegateArgument(instance, del.forceAny(), prop.unsafeCast<T, Any?>())
  }

  private fun siftDelegateCom(del: Command, prop: KProperty1<T, *>): ResolvedComponent {
    TODO("delegate subcommands are currently unsupported")
  }

  // endregion Delegates

  private fun siftFunction(func: KFunction1<T, *>): ResolvedComponent? {
    val annotations = RelevantAnnotations.of(type, func)

    if (!func.isGetter) {
      if (annotations.isNotEmpty) {
        // TODO: This should be a concrete error type
        throw IllegalStateException("${func.qualifiedName(type)} is not a getter, but is annotated as a CLI component")
      }

      return null
    }

    if (annotations.isEmpty) {
      when {
        Flag::class.isSuperclassOf()
      }
      func.returnType.classifier

      // TODO: return type could be a flag itself, not just a value
      return null
    }

    if (annotations.count > 1) {
      throw IllegalStateException("${func.qualifiedName(type)} is annotated as being multiple different types of CLI component")
    }

    // TODO: return type could be a flag itself, not just a value



    func.findAnnotation<CliFlag>()?.then {
      return FauxFlag(CliFlagAnnotationImpl(it), instance, func)
        .apply { validateFlagNames(config) }
    }

    func.findAnnotation<CliArgument>()?.then {
      return FauxArgument(instance, CliArgumentAnnotationImpl(it), GetterReference(type, func))
    }

    func.findAnnotation<CliCommand>()?.then {
      TODO("subcommands by annotation are not yet supported")
    }

    return null
  }

  private fun KAnnotatedElement.findRelevantAnnotations() =
    iterator {
      for (ann in annotations) {
        when (ann) {
          is CliFlag     -> yield(CliFlagAnnotationImpl(ann))
          is CliArgument -> yield(CliArgumentAnnotationImpl(ann))
          is CliCommand  -> yield(CliCommandAnnotation(ann))
        }
      }
    }
}
