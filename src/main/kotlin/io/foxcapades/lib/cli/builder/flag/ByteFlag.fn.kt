package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.ByteFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl

@Suppress("NOTHING_TO_INLINE")
inline fun byteFlag(longForm: String, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun byteFlag(shortForm: Char, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.shortForm = shortForm; action() }

fun byteFlag(action: FlagOptions<Byte>.() -> Unit = {}): ByteFlag =
  ByteFlagImpl(FlagOptions(Byte::class).also(action))

fun nullableByteFlag(action: NullableFlagOptions<Byte>.() -> Unit = {}): Flag<Argument<Byte?>, Byte?> =
  UniversalFlagImpl.of(NullableFlagOptions(Byte::class).also(action))
