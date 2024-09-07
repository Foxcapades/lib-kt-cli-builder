package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.FloatArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class FloatArgumentImpl(
  default: Property<Float>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter: Property<ArgumentFormatter<Float>>,
  filter: Property<ArgumentPredicate<FloatArgument, Float>>,
) : AbstractScalarArgument<FloatArgument, Float>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Float::toString)),
), FloatArgument {
  constructor(opts: ArgOptions<Float>) : this(
    default     = ArgOptions<Float>::default.property(opts),
    isRequired  = ArgOptions<Float>::required.property(opts),
    shouldQuote = ArgOptions<Float>::shouldQuote.property(opts),
    formatter   = ArgOptions<Float>::formatter.property(opts),
    filter      = ArgOptions<Float>::filter.property(opts),
  )
}
