@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.builder.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal inline fun KProperty<*>.hasAnnotation(type: KClass<out Annotation>) =
  annotations.any { type.isInstance(it) }

internal inline fun KProperty<*>.findAnnotation(type: KClass<out Annotation>) =
  annotations.find { type.isInstance(it) }
