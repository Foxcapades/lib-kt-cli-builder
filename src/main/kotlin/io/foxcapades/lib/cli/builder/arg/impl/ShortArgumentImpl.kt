package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.ShortArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal class ShortArgumentImpl(
  default:     Property<Short>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Short>>,
  filter:      Property<ArgumentPredicate<ShortArgument, Short>>,
) : AbstractScalarArgument<ShortArgument, Short>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Short::toString))
), ShortArgument {
  constructor(opts: ArgOptions<Short>) : this(
    default     = ArgOptions<Short>::default.property(opts),
    isRequired  = ArgOptions<Short>::required.property(opts),
    shouldQuote = ArgOptions<Short>::shouldQuote.property(opts),
    formatter   = ArgOptions<Short>::formatter.property(opts),
    filter      = ArgOptions<Short>::filter.property(opts),
  )
}
