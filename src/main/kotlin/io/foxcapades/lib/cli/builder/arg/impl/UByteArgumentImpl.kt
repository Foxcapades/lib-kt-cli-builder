package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.UByteArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal class UByteArgumentImpl(
  default:     Property<UByte>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<UByteArgument, UByte>>,
  formatter:   Property<ArgumentFormatter<UByte>>,
) : AbstractScalarArgument<UByteArgument, UByte>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UByte::toString)),
), UByteArgument {
  constructor(opts: ArgOptions<UByte>) : this(
    default     = ArgOptions<UByte>::default.property(opts),
    isRequired  = ArgOptions<UByte>::required.property(opts),
    shouldQuote = ArgOptions<UByte>::shouldQuote.property(opts),
    formatter   = ArgOptions<UByte>::formatter.property(opts),
    filter      = ArgOptions<UByte>::filter.property(opts),
  )
}
