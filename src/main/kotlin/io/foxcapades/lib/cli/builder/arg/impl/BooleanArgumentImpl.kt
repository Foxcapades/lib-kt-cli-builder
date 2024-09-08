package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.BooleanArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal class BooleanArgumentImpl(
  default:     Property<Boolean>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Boolean>>,
  filter:      Property<ArgumentPredicate<BooleanArgument, Boolean>>,
) : AbstractScalarArgument<BooleanArgument, Boolean>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Boolean::toString)),
), BooleanArgument {
  constructor(opts: ArgOptions<Boolean>) : this(
    default     = ArgOptions<Boolean>::default.property(opts),
    isRequired  = ArgOptions<Boolean>::required.property(opts),
    shouldQuote = ArgOptions<Boolean>::shouldQuote.property(opts),
    formatter   = ArgOptions<Boolean>::formatter.property(opts),
    filter      = ArgOptions<Boolean>::filter.property(opts),
  )
}
