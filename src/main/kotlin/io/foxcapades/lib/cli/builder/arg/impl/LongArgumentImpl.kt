package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.LongArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class LongArgumentImpl(
  default: Property<Long>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<LongArgument, Long>>,
  formatter: Property<ArgumentFormatter<Long>>,
) : AbstractScalarArgument<LongArgument, Long>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Long::toString))
), LongArgument {
  constructor(opts: ArgOptions<Long>) : this(
    default     = ArgOptions<Long>::default.property(opts),
    isRequired  = ArgOptions<Long>::required.property(opts),
    shouldQuote = ArgOptions<Long>::shouldQuote.property(opts),
    formatter   = ArgOptions<Long>::formatter.property(opts),
    filter      = ArgOptions<Long>::filter.property(opts),
  )
}
