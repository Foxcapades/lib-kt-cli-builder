package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*
import java.math.BigDecimal

interface BigDecimalArgument : ScalarArgument<BigDecimal>

fun bigDecimalArg(action: ArgOptions<BigDecimal>.() -> Unit): BigDecimalArgument {
  val opts = ArgOptions(BigDecimal::class).also(action)

  return BigDecimalArgumentImpl(
    default     = ArgOptions<BigDecimal>::default.property(opts),
    isRequired  = ArgOptions<BigDecimal>::required.property(opts),
    shouldQuote = ArgOptions<BigDecimal>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigDecimal>::formatter.property(opts),
    filter      = ArgOptions<BigDecimal>::filter.property(opts)
  )
}

fun nullableBigDecimalArg(action: NullableArgOptions<BigDecimal>.() -> Unit): Argument<BigDecimal?> {
  val opts = NullableArgOptions(BigDecimal::class).also(action)

  return GeneralArgumentImpl(
    type        = BigDecimal::class,
    nullable    = true,
    default     = NullableArgOptions<BigDecimal>::default.property(opts),
    shouldQuote = NullableArgOptions<BigDecimal>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<BigDecimal>::required.property(opts),
    formatter   = NullableArgOptions<BigDecimal>::formatter.property(opts),
    filter      = NullableArgOptions<BigDecimal>::filter.property(opts)
  )
}

internal class BigDecimalArgumentImpl : AbstractScalarArgument<BigDecimalArgument, BigDecimal>, BigDecimalArgument {
  constructor(
    default:     Property<BigDecimal>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<BigDecimal>>,
    filter:      Property<ArgumentPredicate<BigDecimalArgument, BigDecimal>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(BigDecimal::toPlainString)),
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(BigDecimal::toPlainString),
  )
}
