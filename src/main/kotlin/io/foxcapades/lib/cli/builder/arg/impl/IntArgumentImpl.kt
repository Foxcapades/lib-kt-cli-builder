package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.IntArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.reflect.property

internal class IntArgumentImpl(
  default: Property<Int>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<IntArgument, Int>>,
  formatter: Property<ArgumentFormatter<Int>>
) : AbstractScalarArgument<IntArgument, Int>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Int::toString))
), IntArgument {
  constructor(opts: ArgOptions<Int>) : this(
    default     = ArgOptions<Int>::default.property(opts),
    isRequired  = ArgOptions<Int>::required.property(opts),
    shouldQuote = ArgOptions<Int>::shouldQuote.property(opts),
    formatter   = ArgOptions<Int>::formatter.property(opts),
    filter      = ArgOptions<Int>::filter.property(opts),
  )
}
