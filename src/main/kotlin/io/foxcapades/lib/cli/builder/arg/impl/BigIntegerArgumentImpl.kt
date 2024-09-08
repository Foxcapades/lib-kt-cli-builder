package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.BigIntegerArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import java.math.BigInteger

internal class BigIntegerArgumentImpl(
  default:     Property<BigInteger>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<BigInteger>>,
  filter:      Property<ArgumentPredicate<BigIntegerArgument, BigInteger>>
) : AbstractScalarArgument<BigIntegerArgument, BigInteger>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(BigInteger::toString)),
), BigIntegerArgument {
  constructor(opts: ArgOptions<BigInteger>) : this(
    default     = ArgOptions<BigInteger>::default.property(opts),
    isRequired  = ArgOptions<BigInteger>::required.property(opts),
    shouldQuote = ArgOptions<BigInteger>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigInteger>::formatter.property(opts),
    filter      = ArgOptions<BigInteger>::filter.property(opts),
  )
}
