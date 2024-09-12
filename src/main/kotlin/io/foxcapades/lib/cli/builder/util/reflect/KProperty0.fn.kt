@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

internal inline fun KProperty0<*>.hasAnnotation(type: KClass<out Annotation>) =
  annotations.any { type.isInstance(it) }

internal inline fun KProperty0<*>.findAnnotation(type: KClass<out Annotation>) =
  annotations.find { type.isInstance(it) }

@Suppress("UNCHECKED_CAST")
internal inline fun <T> KProperty0<*>.unsafeCast() = this as KProperty0<T>

internal inline fun KProperty0<*>.forceAny() = unsafeCast<Any?>()
