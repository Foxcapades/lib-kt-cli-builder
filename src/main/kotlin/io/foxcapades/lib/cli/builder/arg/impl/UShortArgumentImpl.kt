package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.UShortArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal class UShortArgumentImpl(
  default:     Property<UShort>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<UShortArgument, UShort>>,
  formatter:   Property<ArgumentFormatter<UShort>>,
) : AbstractScalarArgument<UShortArgument, UShort>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UShort::toString)),
), UShortArgument {
  constructor(opts: ArgOptions<UShort>) : this(
    default     = ArgOptions<UShort>::default.property(opts),
    isRequired  = ArgOptions<UShort>::required.property(opts),
    shouldQuote = ArgOptions<UShort>::shouldQuote.property(opts),
    formatter   = ArgOptions<UShort>::formatter.property(opts),
    filter      = ArgOptions<UShort>::filter.property(opts),
  )
}
