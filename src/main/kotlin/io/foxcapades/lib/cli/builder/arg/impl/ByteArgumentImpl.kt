package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.ByteArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class ByteArgumentImpl(
  default: Property<Byte>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter: Property<ArgumentFormatter<Byte>>,
  filter: Property<ArgumentPredicate<ByteArgument, Byte>>
) : AbstractScalarArgument<ByteArgument, Byte>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Byte::toString)),
), ByteArgument {
  constructor(opts: ArgOptions<Byte>) : this(
    default     = ArgOptions<Byte>::default.property(opts),
    isRequired  = ArgOptions<Byte>::required.property(opts),
    shouldQuote = ArgOptions<Byte>::shouldQuote.property(opts),
    formatter   = ArgOptions<Byte>::formatter.property(opts),
    filter      = ArgOptions<Byte>::filter.property(opts),
  )
}
