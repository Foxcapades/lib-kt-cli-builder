@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.CliSerializationException
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.impl.CliCommandAnnotationImpl
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal inline val <T> KCallable<T>.relevantAnnotations: Iterator<CliComponentAnnotation<*>>
  get() = iterator {
    for (ann in annotations) {
      when (ann) {
        is CliFlag     -> yield(CliFlagAnnotationImpl(ann))
        is CliArgument -> yield(CliArgumentAnnotationImpl(ann))
        is CliCommand  -> yield(CliCommandAnnotationImpl(ann))
      }
    }
  }

internal inline fun KCallable<*>.qualifiedName(parent: KClass<*>?) =
  (parent?.qualifiedName ?: "???") + "::" + name

internal inline fun KCallable<*>.makeDuplicateAnnotationsError(parent: KClass<out Any>, type: KClass<out Annotation>) =
  CliSerializationException("${qualifiedName(parent)} has more than one ${type::class.simpleName} annotation")
