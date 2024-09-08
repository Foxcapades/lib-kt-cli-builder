package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.BigDecimalArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import java.math.BigDecimal

internal class BigDecimalArgumentImpl(
  default:     Property<BigDecimal>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<BigDecimal>>,
  filter:      Property<ArgumentPredicate<BigDecimalArgument, BigDecimal>>
) : AbstractScalarArgument<BigDecimalArgument, BigDecimal>(
  default     = default,
  isRequired  = isRequired,
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
