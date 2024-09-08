package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.DoubleArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal class DoubleArgumentImpl(
  default:     Property<Double>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Double>>,
  filter:      Property<ArgumentPredicate<DoubleArgument, Double>>,
) : AbstractScalarArgument<DoubleArgument, Double>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  formatter   = formatter.getOr(ArgumentFormatter(Double::toString)),
  filter      = filter,
), DoubleArgument {
  constructor(opts: ArgOptions<Double>) : this(
    default     = ArgOptions<Double>::default.property(opts),
    isRequired  = ArgOptions<Double>::required.property(opts),
    shouldQuote = ArgOptions<Double>::shouldQuote.property(opts),
    formatter   = ArgOptions<Double>::formatter.property(opts),
    filter      = ArgOptions<Double>::filter.property(opts),
  )
}
