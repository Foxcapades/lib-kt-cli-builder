package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.CharArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class CharArgumentImpl(
  default: Property<Char>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter: Property<ArgumentFormatter<Char>>,
  filter: Property<ArgumentPredicate<CharArgument, Char>>,
) : AbstractScalarArgument<CharArgument, Char>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Char::toString)),
), CharArgument {
  constructor(opts: ArgOptions<Char>) : this(
    default     = ArgOptions<Char>::default.property(opts),
    isRequired  = ArgOptions<Char>::required.property(opts),
    shouldQuote = ArgOptions<Char>::shouldQuote.property(opts),
    formatter   = ArgOptions<Char>::formatter.property(opts),
    filter      = ArgOptions<Char>::filter.property(opts),
  )
}
