package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.*
import io.foxcapades.lib.cli.builder.arg.impl.FauxArgument
import io.foxcapades.lib.cli.builder.arg.impl.PinnedArgument
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.component.ResolvedComponentOld
import io.foxcapades.lib.cli.builder.flag.*
import io.foxcapades.lib.cli.builder.flag.impl.AnnotatedFlag
import io.foxcapades.lib.cli.builder.flag.impl.FauxFlag
import io.foxcapades.lib.cli.builder.flag.impl.PinnedFlag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.*
import io.foxcapades.lib.cli.builder.util.reflect.*
import io.foxcapades.lib.cli.builder.util.then
import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation
import io.foxcapades.lib.cli.builder.command.CliCommand as ComAnn

@Deprecated("needs to be dissolved")
internal class CommandSerializerCore(
  private val instance: Any,
  private val config:   CliSerializationConfig
) {
  private val type: KClass<out Any> = instance::class

  private val typeInfo by lazy { ClassInfo(type) }

  private val commandName: String

  private val components: Iterator<Any>

  init {
    if (instance is Command) {
      instance.getCliCallComponents(config).then { (name, stream) ->
        commandName = name
        components  = stream.iterator()
      }
    } else {
      (type.annotations.findInstance<ComAnn>()
        // TODO: this should be its own exception type
        ?: throw IllegalArgumentException(
          "given type ${type.safeName} cannot be converted into a cli" +
            " string, must be annotated with @CliCommand and/or implement" +
            " CliCallSerializable"))
        .then {
          commandName = it.command
          components  = filterUsableMembers()
        }
    }
  }

  // region Iterator

  fun serializeToIterator() = iterator {
    yield(commandName)

    val serializer = LazyCliAppenderImpl<Any>(components, config)
    while (serializer.hasNext()) {
      val segment = serializer.next()

      yield(segment)
    }
  }

  // endregion Iterator

  // region Stringify

  fun serializeToString(preSize: Int) =
    StringCliAppenderImpl(commandLeader, filterUsableProperties(), config, preSize).toString()

  // endregion Stringify

  // region Component Resolution

  private fun filterUsableMembers() = filterRelevantMembers().filter { it.isUsable }

  private inline val ResolvedComponentOld<Any, Any?>.isUsable get() =
    when (this) {
      is ResolvedFlagOld     -> isUsable()
      is ResolvedArgumentOld -> isUsable()
      else                -> false
    }

  private fun ResolvedFlagOld<Any, Any?>.isUsable() =
    if (isRequired) {
      // TODO: interplay: flag vs argument requirements
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw IllegalStateException("$qualifiedName is marked as required but is not set")
    } else {
      shouldSerialize(config, this)
    }

  private fun ResolvedArgumentOld<Any, Any?>.isUsable() =
    if (isRequired) {
      // TODO: this needs to be its own error type!
      isSet || hasDefault || throw IllegalStateException("$qualifiedName is marked as required but is not set")
    } else {
      shouldSerialize(config, this)
    }

  private fun filterRelevantMembers() =
    typeInfo.mapFilterMembers(::siftMember) { p, _ -> p }

  private fun siftMember(member: KCallable<*>): ResolvedComponent? =
    when (member) {
      is KProperty1<*, *> -> siftProperty(member.forceAny())
      is KFunction1<*, *> -> siftFunction(member.forceAny())
      else                -> null // TODO: LOG HERE
    }

  private fun siftProperty(prop: KProperty1<Any, *>): ResolvedComponent? {
    prop.asDelegateType<CliCallComponent>(instance)?.then {
      return siftDelegateProperty(it, prop)
    }

    val hits = arrayOfNulls<CliComponentAnnotation>(3)
    for (annotation in prop.findRelevantAnnotations()) {
      when (annotation) {
        is CliFlagAnnotation ->
          if (hits[0] == null) hits[0] = annotation else throw duplicateAnnotationsError<CliFlag>(prop)

        is CliArgumentAnnotation ->
          if (hits[1] == null) hits[1] = annotation else throw duplicateAnnotationsError<CliArgument>(prop)

        is CliCommandAnnotation ->
          if (hits[2] == null) hits[2] = annotation else throw duplicateAnnotationsError<CliCommand>(prop)

        else -> BUG()
      }
    }

    val hitCount = hits.count { it != null }

    if (hitCount == 0)
      return null
    if (hitCount > 1)
      throw IllegalStateException("${prop.refStr} is annotated with more than one type of CLI annotation")

    return when {
      hits[0] != null ->
        FauxFlag(instance, hits[0] as CliFlagAnnotation, PropertyReference(type, prop))
          .also { it.validateFlagNames(config) }

      hits[1] != null -> FauxArgument(instance, hits[1] as CliArgumentAnnotation, PropertyReference(type, prop))

      hits[2] != null -> TODO("subcommands are not yet supported")

      else -> BUG()
    }
  }

  // region Delegates

  private fun siftDelegateProperty(del: CliCallComponent, prop: KProperty1<Any, *>): ResolvedComponentOld<Any, Any?> {
    return when (del) {
      is Flag<*, *>  -> siftDelegateFlag(del, prop)
      is Argument<*> -> siftDelegateArg(del, prop)
      is Command     -> siftDelegateCom(del, prop)
      else           -> TODO("custom CliCallComponent types are currently unsupported")
    }
  }

  private fun siftDelegateFlag(del: Flag<*, *>, prop: KProperty1<Any, *>): ResolvedComponentOld<Any, Any?> {
    val annotations = prop.findRelevantAnnotations()
    var flagA: CliFlagAnnotation? = null

    while (annotations.hasNext()) {
      when (val ann = annotations.next()) {
        is CliArgumentAnnotation -> TODO("argument annotations on flag delegate properties are currently unsupported")

        is CliFlagAnnotation ->
          if (flagA != null)
            throw duplicateAnnotationsError<CliFlag>(prop)
          else
            flagA = ann

        is CliCommandAnnotation -> throw IllegalStateException("${prop.refStr} is a flag delegate annotated with @${ComAnn::class.simpleName}")

        else -> BUG()
      }
    }

    return if (flagA != null)
      AnnotatedFlag(type, instance, prop, flagA, del.unsafeCast())
        .also { it.validateFlagNames(config) }
    else
      PinnedFlag(type, instance, prop, del.unsafeCast())
  }

  private fun siftDelegateArg(del: Argument<*>, prop: KProperty1<Any, *>): ResolvedComponent {
    val annotations = prop.findRelevantAnnotations()
    var argA: CliArgumentAnnotation? = null

    while (annotations.hasNext()) {
      when (val ann = annotations.next()) {
        is CliArgumentAnnotation ->
          if (argA != null)
            throw duplicateAnnotationsError<CliArgument>(prop)
          else
            argA = ann

        is CliFlagAnnotation -> TODO("flag annotations on argument delegate properties are currently unsupported")

        is CliCommandAnnotation -> throw IllegalStateException("property ${prop.name} of type ${type.safeName} is an argument delegate annotated with @${ComAnn::class.simpleName}")

        else -> BUG()
      }
    }

    return if (argA != null)
      AnnotatedArgument(type, instance, prop, argA, del.unsafeCast())
    else
      PinnedArgument(type, instance, prop, del.unsafeCast())
  }

  private fun siftDelegateCom(del: Command, prop: KProperty1<Any, *>): ResolvedComponent {
    TODO("delegate subcommands are currently unsupported")
  }

  // endregion Delegates

  private fun siftFunction(func: KFunction1<Any, *>): ResolvedComponent? {
    func.findAnnotation<CliFlag>()?.then {
      return FauxFlag(instance, CliFlagAnnotation(it), GetterReference(type, func))
        .apply { validateFlagNames(config) }
    }

    func.findAnnotation<CliArgument>()?.then {
      return FauxArgument(instance, CliArgumentAnnotation(it), GetterReference(type, func))
    }

    func.findAnnotation<ComAnn>()?.then {
      TODO("subcommands by annotation are not yet supported")
    }

    return null
  }

  private fun KAnnotatedElement.findRelevantAnnotations() =
    iterator {
      for (ann in annotations) {
        when (ann) {
          is CliFlag     -> yield(CliFlagAnnotation(ann))
          is CliArgument -> yield(CliArgumentAnnotation(ann))
          is ComAnn      -> yield(CliCommandAnnotation(ann))
        }
      }
    }

  // endregion Component Resolution

  // region Validation


  // endregion Validation

  private inline fun <reified A : Annotation> duplicateAnnotationsError(prop: KProperty1<*, *>) =
    IllegalStateException("${prop.refStr} has more than one ${A::class.simpleName} annotation")

  private inline val KProperty1<*, *>.refStr get() = "property $name of type ${type.safeName}"
}
