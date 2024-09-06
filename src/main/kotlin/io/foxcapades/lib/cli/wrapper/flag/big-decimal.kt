package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import java.math.BigDecimal

interface BigDecimalFlag : ScalarFlag<BigDecimalArgument, BigDecimal>

// region Constructor Functions

@Suppress("NOTHING_TO_INLINE")
inline fun bigDecimalFlag(longForm: String, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun bigDecimalFlag(shortForm: Char, noinline action: FlagOptions<BigDecimal>.() -> Unit = {}) =
  bigDecimalFlag { this.shortForm = shortForm; action() }

fun bigDecimalFlag(action: FlagOptions<BigDecimal>.() -> Unit = {}): BigDecimalFlag =
  BigDecimalFlagImpl(FlagOptions(BigDecimal::class).also(action))

fun nullableBigDecimalFlag(
  action: NullableFlagOptions<BigDecimal>.() -> Unit = {}
): Flag<Argument<BigDecimal?>, BigDecimal?> =
  GeneralFlagImpl.of(NullableFlagOptions(BigDecimal::class).also(action))

// endregion Constructor Functions

internal class BigDecimalFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<BigDecimalFlag, BigDecimalArgument, BigDecimal>>,
  argument:   BigDecimalArgument,
)
  : AbstractFlagImpl<BigDecimalFlag, BigDecimalArgument, BigDecimal>(
    longForm,
    shortForm,
    isRequired,
    filter,
    argument,
  )
  , BigDecimalFlag
{
  constructor(opts: FlagOptions<BigDecimal>) : this(
    longForm   = FlagOptions<BigDecimal>::longForm.property(opts),
    shortForm  = FlagOptions<BigDecimal>::shortForm.property(opts),
    isRequired = FlagOptions<BigDecimal>::required.property(opts),
    filter     = FlagOptions<BigDecimal>::flagFilter.property(opts),
    argument   = BigDecimalArgumentImpl(opts.argument)
  )
}
