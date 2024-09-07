package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.StringArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class StringArgumentImpl(
  default: Property<String>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<StringArgument, String>>,
  formatter: Property<ArgumentFormatter<String>>
) : AbstractScalarArgument<StringArgument, String>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(true),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(String::toString))
), StringArgument {
  constructor(opts: ArgOptions<String>) : this(
    default     = ArgOptions<String>::default.property(opts),
    isRequired  = ArgOptions<String>::required.property(opts),
    shouldQuote = ArgOptions<String>::shouldQuote.property(opts),
    formatter   = ArgOptions<String>::formatter.property(opts),
    filter      = ArgOptions<String>::filter.property(opts),
  )
}
