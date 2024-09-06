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

fun bigIntegerFlag(action: FlagOptions<BigInteger>.() -> Unit = {}): BigIntegerFlag =
  BigIntegerFlagImpl(FlagOptions(BigInteger::class).also(action))

fun nullableBigIntegerFlag(
  action: NullableFlagOptions<BigInteger>.() -> Unit = {}
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
{
  constructor(opts: FlagOptions<BigInteger>) : this(
    longForm   = FlagOptions<BigInteger>::longForm.property(opts),
    shortForm  = FlagOptions<BigInteger>::shortForm.property(opts),
    isRequired = FlagOptions<BigInteger>::required.property(opts),
    filter     = FlagOptions<BigInteger>::flagFilter.property(opts),
    argument   = BigIntegerArgumentImpl(opts.argument)
  )
}
