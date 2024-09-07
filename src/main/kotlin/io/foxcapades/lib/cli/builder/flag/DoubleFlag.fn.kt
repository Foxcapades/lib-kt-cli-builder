package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.DoubleFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun doubleFlag(longForm: String, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun doubleFlag(shortForm: Char, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.shortForm = shortForm; action() }

fun doubleFlag(action: FlagOptions<Double>.() -> Unit = {}): DoubleFlag =
  DoubleFlagImpl(FlagOptions(Double::class).also(action))

fun nullableDoubleFlag(action: NullableFlagOptions<Double>.() -> Unit = {}): Flag<Argument<Double?>, Double?> =
  UniversalFlagImpl.of(NullableFlagOptions(Double::class).also(action))
