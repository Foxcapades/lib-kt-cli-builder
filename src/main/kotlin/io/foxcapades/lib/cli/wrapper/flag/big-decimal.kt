package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import java.math.BigDecimal

interface BigDecimalFlag : ScalarFlag<BigDecimalArgument, BigDecimal>

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
    isRequired = FlagOptions<BigDecimal>::requireFlag.property(flag),
    argument   = BigDecimalArgumentImpl(
      default     = FlagOptions<BigDecimal>::default.property(flag),
      isRequired  = FlagOptions<BigDecimal>::requireArg.property(flag),
      shouldQuote = FlagOptions<BigDecimal>::shouldQuote.property(flag),
      formatter   = FlagOptions<BigDecimal>::formatter.property(flag),
    )
  )
}

fun nullableBigDecimalFlag(action: NullableFlagOptions<BigDecimal>.() -> Unit): Flag<Argument<BigDecimal?>, BigDecimal?> {
  val flag = NullableFlagOptions(BigDecimal::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<BigDecimal>::longForm.property(flag),
    shortForm  = NullableFlagOptions<BigDecimal>::shortForm.property(flag),
    isRequired = NullableFlagOptions<BigDecimal>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      BigDecimal::class,
      true,
      default     = NullableFlagOptions<BigDecimal>::default.property(flag),
      shouldQuote = NullableFlagOptions<BigDecimal>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<BigDecimal>::requireArg.property(flag),
      formatter   = NullableFlagOptions<BigDecimal>::formatter.property(flag),
    )
  )
}

internal class BigDecimalFlagImpl(
  longForm:   Property<String>   = Property.empty(),
  shortForm:  Property<Char>     = Property.empty(),
  isRequired: Property<Boolean>  = Property.empty(),
  argument:   BigDecimalArgument = BigDecimalArgumentImpl(false),
)
  : AbstractFlagImpl<BigDecimalArgument, BigDecimal>(
    longForm,
    shortForm,
    isRequired,
    argument,
  )
  , BigDecimalFlag
