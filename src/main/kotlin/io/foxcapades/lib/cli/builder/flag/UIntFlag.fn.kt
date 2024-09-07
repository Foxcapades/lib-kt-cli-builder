package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.UIntFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun uintFlag(longForm: String, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun uintFlag(shortForm: Char, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.shortForm = shortForm; action() }

fun uintFlag(action: FlagOptions<UInt>.() -> Unit = {}): UIntFlag =
  UIntFlagImpl(FlagOptions(UInt::class).also(action))

fun nullableUIntFlag(action: NullableFlagOptions<UInt>.() -> Unit = {}): Flag<Argument<UInt?>, UInt?> =
  UniversalFlagImpl.of(NullableFlagOptions(UInt::class).also(action))

