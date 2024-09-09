@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.arg

@Suppress("UNCHECKED_CAST")
internal inline fun <T> Argument<*>.unsafeCast() = this as Argument<T>

internal inline fun Argument<*>.forceAny() = unsafeCast<Any?>()
