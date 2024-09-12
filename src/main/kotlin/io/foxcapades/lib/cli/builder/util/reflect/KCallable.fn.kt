@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.util.takeAs
import io.foxcapades.lib.cli.builder.util.toList
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSuperclassOf

internal inline val <T> KCallable<T>.relevantAnnotations: Iterator<CliComponentAnnotation<*>>
  get() = iterator {
    for (ann in annotations) {
      when (ann) {
        is CliFlag     -> yield(CliFlagAnnotationImpl(ann))
        is CliArgument -> yield(CliArgumentAnnotationImpl(ann))
        is CliCommand  -> yield(CliCommandAnnotation(ann))
      }
    }
  }

internal inline fun <T> KCallable<T>.getRelevantAnnotations() =
  relevantAnnotations.toList()

internal inline fun KCallable<*>.qualifiedName(parent: KClass<*>?) =
  (parent?.qualifiedName ?: "???") + "::" + name

internal inline fun <reified A : Annotation> KCallable<*>.makeDuplicateAnnotationsError(type: KClass<out Any>) =
  makeDuplicateAnnotationsError(type, A::class)

internal inline fun KCallable<*>.makeDuplicateAnnotationsError(parent: KClass<out Any>, type: KClass<out Annotation>) =
  IllegalStateException("${qualifiedName(parent)} has more than one ${type::class.simpleName} annotation")
