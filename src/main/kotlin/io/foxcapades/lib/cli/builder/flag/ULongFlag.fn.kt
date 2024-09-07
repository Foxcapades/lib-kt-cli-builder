package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.ULongFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun ulongFlag(longForm: String, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ulongFlag(shortForm: Char, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.shortForm = shortForm; action() }

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit = {}): ULongFlag =
  ULongFlagImpl(FlagOptions(ULong::class).also(action))

fun nullableULongFlag(action: NullableFlagOptions<ULong>.() -> Unit = {}): Flag<Argument<ULong?>, ULong?> =
  UniversalFlagImpl.of(NullableFlagOptions(ULong::class).also(action))

