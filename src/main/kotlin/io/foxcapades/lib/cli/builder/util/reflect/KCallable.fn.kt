@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation
import io.foxcapades.lib.cli.builder.flag.CliFlag
import io.foxcapades.lib.cli.builder.flag.impl.CliFlagAnnotationImpl
import io.foxcapades.lib.cli.builder.util.toList
import kotlin.reflect.KCallable

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
