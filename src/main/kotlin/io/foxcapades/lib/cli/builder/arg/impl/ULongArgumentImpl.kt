package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.ULongArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class ULongArgumentImpl(
  default: Property<ULong>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<ULongArgument, ULong>>,
  formatter: Property<ArgumentFormatter<ULong>>,
) : AbstractScalarArgument<ULongArgument, ULong>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(ULong::toString)),
), ULongArgument {
  constructor(opts: ArgOptions<ULong>) : this(
    default     = ArgOptions<ULong>::default.property(opts),
    isRequired  = ArgOptions<ULong>::required.property(opts),
    shouldQuote = ArgOptions<ULong>::shouldQuote.property(opts),
    formatter   = ArgOptions<ULong>::formatter.property(opts),
    filter      = ArgOptions<ULong>::filter.property(opts),
  )
}
