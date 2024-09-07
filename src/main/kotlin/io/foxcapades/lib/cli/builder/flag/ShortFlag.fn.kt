package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.ShortFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun shortFlag(longForm: String, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun shortFlag(shortForm: Char, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.shortForm = shortForm; action() }

fun shortFlag(action: FlagOptions<Short>.() -> Unit = {}): ShortFlag =
  ShortFlagImpl(FlagOptions(Short::class).also(action))

fun nullableShortFlag(action: NullableFlagOptions<Short>.() -> Unit = {}): Flag<Argument<Short?>, Short?> =
  UniversalFlagImpl.of(NullableFlagOptions(Short::class).also(action))
