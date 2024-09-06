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

fun bigDecimalFlag(action: FlagOptions<BigDecimal>.() -> Unit = {}): BigDecimalFlag {
  val flag = FlagOptions(BigDecimal::class).also(action)

  return BigDecimalFlagImpl(
    longForm   = FlagOptions<BigDecimal>::longForm.property(flag),
    shortForm  = FlagOptions<BigDecimal>::shortForm.property(flag),
    isRequired = FlagOptions<BigDecimal>::required.property(flag),
    filter     = FlagOptions<BigDecimal>::flagFilter.property(flag),
    argument   = BigDecimalArgumentImpl(
      default     = ArgOptions<BigDecimal>::default.property(flag.argument),
      isRequired  = ArgOptions<BigDecimal>::required.property(flag.argument),
      shouldQuote = ArgOptions<BigDecimal>::shouldQuote.property(flag.argument),
      filter      = ArgOptions<BigDecimal>::filter.property(flag.argument),
      formatter   = ArgOptions<BigDecimal>::formatter.property(flag.argument),
    )
  )
}

fun nullableBigDecimalFlag(
  action: NullableFlagOptions<BigDecimal>.() -> Unit
): Flag<Argument<BigDecimal?>, BigDecimal?> =
  GeneralFlagImpl.of(NullableFlagOptions(BigDecimal::class).also(action))

// endregion Constructor Functions

internal class BigDecimalFlagImpl(
  longForm:   Property<String> = Property(),
  shortForm:  Property<Char> = Property(),
  isRequired: Property<Boolean> = Property(),
  filter:     Property<FlagPredicate<BigDecimalFlag, BigDecimalArgument, BigDecimal>> = Property(),
  argument:   BigDecimalArgument = BigDecimalArgumentImpl(false),
)
  : AbstractFlagImpl<BigDecimalFlag, BigDecimalArgument, BigDecimal>(
    longForm,
    shortForm,
    isRequired,
    filter,
    argument,
  )
  , BigDecimalFlag
