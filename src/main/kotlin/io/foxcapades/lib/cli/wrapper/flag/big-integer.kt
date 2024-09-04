package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import java.math.BigInteger

interface BigIntegerFlag : ScalarFlag<BigIntegerArgument, BigInteger>

@Suppress("NOTHING_TO_INLINE")
inline fun bigIntegerFlag(longForm: String, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun bigIntegerFlag(shortForm: Char, noinline action: FlagOptions<BigInteger>.() -> Unit = {}) =
  bigIntegerFlag { this.shortForm = shortForm; action() }

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit): BigIntegerFlag {
  val flag = FlagOptions(BigInteger::class).also(action)

  return BigIntegerFlagImpl(
    longForm   = FlagOptions<BigInteger>::longForm.property(flag),
    shortForm  = FlagOptions<BigInteger>::shortForm.property(flag),
    isRequired = FlagOptions<BigInteger>::requireFlag.property(flag),
    argument   = BigIntegerArgumentImpl(
      default     = FlagOptions<BigInteger>::default.property(flag),
      isRequired  = FlagOptions<BigInteger>::requireArg.property(flag),
      shouldQuote = FlagOptions<BigInteger>::shouldQuote.property(flag),
      formatter   = FlagOptions<BigInteger>::formatter.property(flag),
    )
  )
}

fun nullableBigIntegerFlag(action: NullableFlagOptions<BigInteger>.() -> Unit): Flag<Argument<BigInteger?>, BigInteger?> {
  val flag = NullableFlagOptions(BigInteger::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<BigInteger>::longForm.property(flag),
    shortForm  = NullableFlagOptions<BigInteger>::shortForm.property(flag),
    isRequired = NullableFlagOptions<BigInteger>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      BigInteger::class,
      true,
      default     = NullableFlagOptions<BigInteger>::default.property(flag),
      shouldQuote = NullableFlagOptions<BigInteger>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<BigInteger>::requireArg.property(flag),
      formatter   = NullableFlagOptions<BigInteger>::formatter.property(flag),
    )
  )
}

internal class BigIntegerFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  argument: BigIntegerArgument
)
  : AbstractFlagImpl<BigIntegerArgument, BigInteger>(
    longForm,
    shortForm,
    isRequired,
    argument,
  ),
  BigIntegerFlag
