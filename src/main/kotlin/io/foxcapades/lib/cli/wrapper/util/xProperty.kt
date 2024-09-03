@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.wrapper.util

internal inline fun <T> Property<T>.getOrNull() = if(isSet) get() else null

internal inline fun <T> Property<T>.getOr(fallback: T) = getOrNull() ?: fallback

internal inline fun <T> Property<T>.getOrCompute(fn: () -> T) = getOrNull() ?: fn()

internal inline fun <T> Property<T>.getOrThrow(ex: Throwable) = getOrNull() ?: throw ex

internal inline fun <T> Property<T>.getOrThrow(fn: () -> Throwable) = getOrNull() ?: throw fn()

internal inline fun <T> MutableProperty<T>.setIfAbsent(value: T) =
  also { if (!isSet) set(value) }
