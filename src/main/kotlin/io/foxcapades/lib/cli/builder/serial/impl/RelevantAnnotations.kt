package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.reflect.makeDuplicateAnnotationsError
import io.foxcapades.lib.cli.builder.util.reflect.relevantAnnotations
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

internal data class RelevantAnnotations(
  val flag:     CliFlagAnnotationImpl?     = null,
  val argument: CliArgumentAnnotationImpl? = null,
  val command:  CliCommandAnnotation?      = null,
) {
  inline val hasFlagAnnotation
    get() = flag != null

  inline val hasArgumentAnnotation
    get() = argument != null

  inline val hasCommandAnnotation
    get() = command != null

  inline val isEmpty
    get() = count == 0

  inline val isNotEmpty
    get() = count > 0

  val count: Int

  init {
    var i = 0

    if (hasFlagAnnotation) i++
    if (hasArgumentAnnotation) i++
    if (hasCommandAnnotation) i++

    count = i
  }

  class DuplicateException(val annotation: Annotation) : RuntimeException()

  companion object {
    @JvmStatic
    fun of(it: Iterator<CliComponentAnnotation<*>>): RelevantAnnotations {
      var flag:     CliFlagAnnotationImpl?     = null
      var argument: CliArgumentAnnotationImpl? = null
      var command:  CliCommandAnnotation?  = null

      for (ann in it) {
        when (ann) {
          is CliFlagAnnotationImpl     -> if (flag     == null) flag     = ann else throw DuplicateException(ann.annotation)
          is CliArgumentAnnotationImpl -> if (argument == null) argument = ann else throw DuplicateException(ann.annotation)
          is CliCommandAnnotation      -> if (command  == null) command  = ann else throw DuplicateException(ann.annotation)
          else                         -> BUG()
        }
      }

      return RelevantAnnotations(flag, argument, command)
    }

    @JvmStatic
    @Deprecated("this needs to live elsewhere, reflective bullshit doesn't belong here")
    fun of(parent: KClass<*>, prop: KFunction1<*, *>): RelevantAnnotations {
      return try {
        of(prop.relevantAnnotations)
      } catch (e: DuplicateException) {
        throw prop.makeDuplicateAnnotationsError(parent, e.annotation::class)
      }
    }

    @JvmStatic
    @Deprecated("this needs to live elsewhere, reflective bullshit doesn't belong here")
    fun of(parent: KClass<*>, prop: KProperty1<*, *>): RelevantAnnotations {
      return try {
        of(prop.relevantAnnotations)
      } catch (e: DuplicateException) {
        throw prop.makeDuplicateAnnotationsError(parent, e.annotation::class)
      }
    }
  }
}
