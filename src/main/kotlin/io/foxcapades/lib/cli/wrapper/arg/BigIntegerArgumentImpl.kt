package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import java.math.BigInteger

internal class BigIntegerArgumentImpl : AbstractScalarArgument<BigInteger>, BigIntegerArgument {
  constructor(
    default: Property<BigInteger>,
    shouldQuote: Property<Boolean>,
    formatter: Property<ArgumentFormatter<BigInteger>>
  ) : super(
    default     = default.getOr(BigInteger.ZERO),
    hasDefault  = default.isSet,
    shouldQuote = shouldQuote.getOr(false),
    formatter.getOr(ArgumentFormatter(BigInteger::toString))
  )

  constructor(default: BigInteger, formatter: ArgumentFormatter<BigInteger>)
    : super(default, true, false, formatter)

  constructor(default: BigInteger)
    : super(default, true, false, ArgumentFormatter(BigInteger::toString))

  constructor(formatter: ArgumentFormatter<BigInteger>)
    : super(BigInteger.ZERO, false, false, formatter)

  constructor()
    : super(BigInteger.ZERO, false, false, ArgumentFormatter(BigInteger::toString))
}
