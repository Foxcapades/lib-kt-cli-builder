package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.FloatFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun floatFlag(longForm: String, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun floatFlag(shortForm: Char, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.shortForm = shortForm; action() }

fun floatFlag(action: FlagOptions<Float>.() -> Unit = {}): FloatFlag =
  FloatFlagImpl(FlagOptions(Float::class).also(action))

fun nullableFloatFlag(action: NullableFlagOptions<Float>.() -> Unit = {}): Flag<Argument<Float?>, Float?> =
  UniversalFlagImpl.of(NullableFlagOptions(Float::class).also(action))

