@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.CliSerializationException
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal inline fun KCallable<*>.qualifiedName(parent: KClass<*>?) =
  (parent?.qualifiedName ?: "???") + "::" + name

internal inline fun KCallable<*>.makeDuplicateAnnotationsError(parent: KClass<out Any>, type: KClass<out Annotation>) =
  CliSerializationException("${qualifiedName(parent)} has more than one ${type::class.simpleName} annotation")
