package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.CliSerializationException
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.impl.CliCommandAnnotationImpl
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.CliFlagAnnotation
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.logger
import io.foxcapades.lib.cli.builder.util.reflect.MemberInfo
import io.foxcapades.lib.cli.builder.util.reflect.makeDuplicateAnnotationsError
import io.foxcapades.lib.cli.builder.util.reflect.safeName
import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

internal abstract class AbstractComponentResolver<T : Any>(
  parent: ResolvedCommand<T>,
  config: CliSerializationConfig
)
  : Iterator<ResolvedComponent>
{
  protected val logger = logger()

  protected val parent = parent

  protected val config = config

  abstract val type: KClass<out T>

  // region Type Determination

  protected fun KCallable<*>.determineValueType() = returnType.determineValueType()

  private fun KType.determineValueType() =
    when (val c = classifier) {
      is KClass<*>      -> c.determineValueType()
      is KTypeParameter -> c.determineValueType()
      else              -> ValueType.Value
    }

  private fun KClass<*>.determineValueType(): ValueType =
    when {
      isSubclassOf(Flag::class)     -> ValueType.Flag
      isSubclassOf(Argument::class) -> ValueType.Argument
      isSubclassOf(Command::class)  -> ValueType.Command

      else -> {
        if (isSubclassOf(CliCallComponent::class))
          logger.warn("ignoring unknown CliCallComponent subtype {}", this)

        if (findAnnotation<CliCommand>() == null)
          ValueType.Value
        else
          ValueType.AnnotatedCommand
      }
    }

  private fun KTypeParameter.determineValueType(): ValueType {
    for (bound in upperBounds) {
      val vt = bound.determineValueType()

      if (vt != ValueType.Value)
        return vt
    }

    return ValueType.Value
  }

  protected enum class ValueType {
    Flag, Argument, Command, AnnotatedCommand, Value
  }

  // endregion Type Determination


  // region Annotation Search

  protected fun MemberInfo<out Any, out KCallable<*>>.relevantAnnotations(): RelevantAnnotations {
    var flag:     CliFlagAnnotation?     = null
    var argument: CliArgumentAnnotation? = null
    var command:  CliCommandAnnotation?      = null

    val fn = when (member) {
      is KProperty<*> -> { t: KClass<out Any>, a: KClass<out Annotation> -> member.makeDuplicateAnnotationsError(t, a) }
      is KFunction<*> -> { t: KClass<out Any>, a: KClass<out Annotation> -> member.makeDuplicateAnnotationsError(t, a) }
      else -> BUG()
    }

    for (ann in filterRelevantAnnotations()) {
      when (ann) {
        is CliFlagAnnotation     -> if (flag     == null) flag     = ann else throw fn(type, ann.annotation::class)
        is CliArgumentAnnotation -> if (argument == null) argument = ann else throw fn(type, ann.annotation::class)
        is CliCommandAnnotation  -> if (command  == null) command  = ann else throw fn(type, ann.annotation::class)
        else                     -> BUG()
      }
    }

    var i = 0

    if (flag != null) i++
    if (argument != null) i++
    if (command != null) i++

    if (i > 1)
      throw CliSerializationException("Argument property \"${name}\" provided by ${type.safeName} is annotated as more than one type of CLI component")

    return RelevantAnnotations(flag, argument, command)
  }

  protected fun KCallable<*>.relevantAnnotations(): RelevantAnnotations {
    var flag:     CliFlagAnnotation?     = null
    var argument: CliArgumentAnnotation? = null
    var command:  CliCommandAnnotation?      = null

    val fn = when (this) {
      is KProperty<*> -> { t: KClass<out Any>, a: KClass<out Annotation> -> makeDuplicateAnnotationsError(t, a) }
      is KFunction<*> -> { t: KClass<out Any>, a: KClass<out Annotation> -> makeDuplicateAnnotationsError(t, a) }
      else -> BUG()
    }

    for (ann in filterRelevantAnnotations()) {
      when (ann) {
        is CliFlagAnnotation     -> if (flag     == null) flag     = ann else throw fn(type, ann.annotation::class)
        is CliArgumentAnnotation -> if (argument == null) argument = ann else throw fn(type, ann.annotation::class)
        is CliCommandAnnotation  -> if (command  == null) command  = ann else throw fn(type, ann.annotation::class)
        else                     -> BUG()
      }
    }

    var i = 0

    if (flag != null) i++
    if (argument != null) i++
    if (command != null) i++

    if (i > 1)
      throw CliSerializationException("Argument property \"${name}\" provided by ${type.safeName} is annotated as more than one type of CLI component")

    return RelevantAnnotations(flag, argument, command)
  }

  private fun MemberInfo<out Any, out KCallable<*>>.filterRelevantAnnotations(): Sequence<CliComponentAnnotation<*>> =
    annotations()
      .mapNotNull { when (it) {
        is CliFlag     -> CliFlagAnnotationImpl(it)
        is CliArgument -> CliArgumentAnnotationImpl(it)
        is CliCommand  -> CliCommandAnnotationImpl(it)
        else           -> null
      } }

  private fun KCallable<*>.filterRelevantAnnotations(): Sequence<CliComponentAnnotation<*>> =
    annotations
      .asSequence()
      .mapNotNull { when (it) {
        is CliFlag     -> CliFlagAnnotationImpl(it)
        is CliArgument -> CliArgumentAnnotationImpl(it)
        is CliCommand  -> CliCommandAnnotationImpl(it)
        else           -> null
      } }

  protected data class RelevantAnnotations(
    val flag:     CliFlagAnnotation?     = null,
    val argument: CliArgumentAnnotation? = null,
    val command:  CliCommandAnnotation?      = null,
  ) {
    inline val hasFlagAnnotation
      get() = flag != null

    inline val hasArgumentAnnotation
      get() = argument != null

    inline val hasCommandAnnotation
      get() = command != null

    inline val isEmpty
      get() = flag == null && argument == null && command == null
  }

  // endregion Annotation Search


  // Temp helpers

  @Suppress("FunctionName")
  @Deprecated("implement this", replaceWith = ReplaceWith("own implementation"))
  protected fun <T> SUB_COMMAND(): T = TODO("subcommands are currently unsupported")
}
