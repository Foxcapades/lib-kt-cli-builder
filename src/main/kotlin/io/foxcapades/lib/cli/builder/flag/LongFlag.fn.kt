package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.LongFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun longFlag(longForm: String, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun longFlag(shortForm: Char, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.shortForm = shortForm; action() }

fun longFlag(action: FlagOptions<Long>.() -> Unit = {}): LongFlag =
  LongFlagImpl(FlagOptions(Long::class).also(action))

fun nullableLongFlag(action: NullableFlagOptions<Long>.() -> Unit = {}): Flag<Argument<Long?>, Long?> =
  UniversalFlagImpl.of(NullableFlagOptions(Long::class).also(action))
