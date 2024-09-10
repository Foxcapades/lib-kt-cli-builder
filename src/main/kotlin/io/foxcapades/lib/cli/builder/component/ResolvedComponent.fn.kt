@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.component

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

@Suppress("UNCHECKED_CAST")
internal inline fun ResolvedComponentOld<*, *>.forceAny() = this as ResolvedComponentOld<Any, Any?>

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> ResolvedComponent.tryAsValueAccessor() =
  this as? ValueAccessorReference<T, V, KCallable<V>>
