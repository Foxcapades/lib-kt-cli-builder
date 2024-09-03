package io.foxcapades.lib.cli.wrapper.util

internal inline fun <T, reified R : Any> T.takeAs(): R? = takeIf { it is R }?.let { it as R }

internal inline fun <T> T.then(fn: (T) -> Unit) = fn(this)

@Suppress("NOTHING_TO_INLINE")
internal inline fun StringBuilder.dump() = toString().also { clear() }