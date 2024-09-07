package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.UIntArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class UIntArgumentImpl(
  default: Property<UInt>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<UIntArgument, UInt>>,
  formatter: Property<ArgumentFormatter<UInt>>,
) : AbstractScalarArgument<UIntArgument, UInt>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UInt::toString)),
), UIntArgument {
  constructor(opts: ArgOptions<UInt>) : this(
    default     = ArgOptions<UInt>::default.property(opts),
    isRequired  = ArgOptions<UInt>::required.property(opts),
    shouldQuote = ArgOptions<UInt>::shouldQuote.property(opts),
    formatter   = ArgOptions<UInt>::formatter.property(opts),
    filter      = ArgOptions<UInt>::filter.property(opts),
  )
}
