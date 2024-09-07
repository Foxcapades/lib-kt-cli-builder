package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.UShortFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl


@Suppress("NOTHING_TO_INLINE")
inline fun ushortFlag(longForm: String, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ushortFlag(shortForm: Char, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.shortForm = shortForm; action() }

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit = {}): UShortFlag =
  UShortFlagImpl(FlagOptions(UShort::class).also(action))

fun nullableUShortFlag(action: NullableFlagOptions<UShort>.() -> Unit = {}): Flag<Argument<UShort?>, UShort?> =
  UniversalFlagImpl.of(NullableFlagOptions(UShort::class).also(action))
