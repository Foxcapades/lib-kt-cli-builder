package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*
import java.math.BigDecimal

interface BigDecimalArgument : ScalarArgument<BigDecimal>

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): BigDecimalArgument =
  BigDecimalArgumentImpl(ArgOptions(BigDecimal::class).also(action))

fun nullableBigDecimalArg(action: NullableArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal?> =
  GeneralArgumentImpl.of(NullableArgOptions(BigDecimal::class).also(action))

internal class BigDecimalArgumentImpl(
  default:     Property<BigDecimal>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<BigDecimal>>,
  filter:      Property<ArgumentPredicate<BigDecimalArgument, BigDecimal>>
) : AbstractScalarArgument<BigDecimalArgument, BigDecimal>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(BigDecimal::toPlainString)),
), BigDecimalArgument {
  constructor(opts: ArgOptions<BigDecimal>) : this(
    default     = ArgOptions<BigDecimal>::default.property(opts),
    isRequired  = ArgOptions<BigDecimal>::required.property(opts),
    shouldQuote = ArgOptions<BigDecimal>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigDecimal>::formatter.property(opts),
    filter      = ArgOptions<BigDecimal>::filter.property(opts),
  )
}
