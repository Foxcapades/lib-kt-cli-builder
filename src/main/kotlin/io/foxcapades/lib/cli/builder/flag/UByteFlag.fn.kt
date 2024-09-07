package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.UByteFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun ubyteFlag(longForm: String, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ubyteFlag(shortForm: Char, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.shortForm = shortForm; action() }

fun ubyteFlag(action: FlagOptions<UByte>.() -> Unit = {}): UByteFlag =
  UByteFlagImpl(FlagOptions(UByte::class).also(action))

fun nullableUByteFlag(action: NullableFlagOptions<UByte>.() -> Unit = {}): Flag<Argument<UByte?>, UByte?> =
  UniversalFlagImpl.of(NullableFlagOptions(UByte::class).also(action))

