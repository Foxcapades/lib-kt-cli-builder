package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
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

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit = {}): BigIntegerFlag {
  val flag = FlagOptions(BigInteger::class).also(action)

  return BigIntegerFlagImpl(
    longForm   = FlagOptions<BigInteger>::longForm.property(flag),
    shortForm  = FlagOptions<BigInteger>::shortForm.property(flag),
    isRequired = FlagOptions<BigInteger>::required.property(flag),
    filter     = FlagOptions<BigInteger>::flagFilter.property(flag),
    argument   = BigIntegerArgumentImpl(
      default     = ArgOptions<BigInteger>::default.property(flag.argument),
      isRequired  = ArgOptions<BigInteger>::required.property(flag.argument),
      shouldQuote = ArgOptions<BigInteger>::shouldQuote.property(flag.argument),
      filter      = ArgOptions<BigInteger>::filter.property(flag.argument),
      formatter   = ArgOptions<BigInteger>::formatter.property(flag.argument),
    )
  )
}

fun nullableBigIntegerFlag(
  action: NullableFlagOptions<BigInteger>.() -> Unit
): Flag<Argument<BigInteger?>, BigInteger?> =
  GeneralFlagImpl.of(NullableFlagOptions(BigInteger::class).also(action))

internal class BigIntegerFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<BigIntegerFlag, BigIntegerArgument, BigInteger>>,
  argument:   BigIntegerArgument
)
  : AbstractFlagImpl<BigIntegerFlag, BigIntegerArgument, BigInteger>(
    longForm,
    shortForm,
    isRequired,
    filter,
    argument,
  ),
  BigIntegerFlag
