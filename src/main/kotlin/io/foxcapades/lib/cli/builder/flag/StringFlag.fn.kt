package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.StringFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun stringFlag(longForm: String, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun stringFlag(shortForm: Char, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.shortForm = shortForm; action() }

fun stringFlag(action: FlagOptions<String>.() -> Unit = {}): StringFlag =
  StringFlagImpl(FlagOptions(String::class).also(action))

fun nullableStringFlag(action: NullableFlagOptions<String>.() -> Unit = {}): Flag<Argument<String?>, String?> =
  UniversalFlagImpl.of(NullableFlagOptions(String::class).also(action))
