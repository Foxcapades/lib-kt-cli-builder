package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.IntFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun intFlag(longForm: String, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun intFlag(shortForm: Char, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.shortForm = shortForm; action() }

fun intFlag(action: FlagOptions<Int>.() -> Unit = {}): IntFlag =
  IntFlagImpl(FlagOptions(Int::class).also(action))

fun nullableIntFlag(action: NullableFlagOptions<Int>.() -> Unit = {}): Flag<Argument<Int?>, Int?> =
  UniversalFlagImpl.of(NullableFlagOptions(Int::class).also(action))
