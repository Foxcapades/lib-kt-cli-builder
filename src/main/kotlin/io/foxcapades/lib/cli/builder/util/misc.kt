package io.foxcapades.lib.cli.builder.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

internal inline fun <T, reified R : Any> T.takeAs(): R? =
  takeIf { it is R }?.let { it as R }

@OptIn(ExperimentalContracts::class)
internal inline fun <T> T.then(fn: (T) -> Unit) {
  contract { callsInPlace(fn, InvocationKind.EXACTLY_ONCE) }
  fn(this)
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun StringBuilder.dump() = toString().also { clear() }
