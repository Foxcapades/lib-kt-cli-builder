package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.property
import java.math.BigInteger

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit): BigIntegerFlag {
  val flag = FlagOptions(BigInteger::class).also(action)

  return BigIntegerFlagImpl(
    longForm   = FlagOptions<BigInteger>::longForm.property(flag),
    shortForm  = FlagOptions<BigInteger>::shortForm.property(flag),
    isRequired = FlagOptions<BigInteger>::requireFlag.property(flag),
    argument   = BigIntegerArgumentImpl(
      default     = FlagOptions<BigInteger>::default.property(flag),
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
