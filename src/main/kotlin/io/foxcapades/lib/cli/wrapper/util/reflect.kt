package io.foxcapades.lib.cli.wrapper.util

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal inline val KClass<*>.safeName
  get() = qualifiedName ?: simpleName ?: "unknown"

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <T> KProperty1<*, *>.property(instance: Any) =
  (this as KProperty1<Any, *>).getDelegate(instance) as SimpleProperty<T>
