package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.CharFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(longForm: String, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(shortForm: Char, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.shortForm = shortForm; action() }

fun charFlag(action: FlagOptions<Char>.() -> Unit = {}): CharFlag =
  CharFlagImpl(FlagOptions(Char::class).also(action))

fun nullableCharFlag(action: NullableFlagOptions<Char>.() -> Unit = {}): Flag<Argument<Char?>, Char?> =
  UniversalFlagImpl.of(NullableFlagOptions(Char::class).also(action))

