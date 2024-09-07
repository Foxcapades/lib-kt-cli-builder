package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.BigIntegerFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import java.math.BigInteger

@Suppress("NOTHING_TO_INLINE")
inline fun bigIntegerFlag(longForm: String, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun bigIntegerFlag(shortForm: Char, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.shortForm = shortForm; action() }

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit = {}): BigIntegerFlag =
  BigIntegerFlagImpl(FlagOptions(BigInteger::class).also(action))

fun nullableBigIntegerFlag(
  action: NullableFlagOptions<BigInteger>.() -> Unit = {}
): Flag<Argument<BigInteger?>, BigInteger?> =
  UniversalFlagImpl.of(NullableFlagOptions(BigInteger::class).also(action))

