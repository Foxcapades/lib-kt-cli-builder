package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigInteger

internal class BigIntegerArgumentImpl : AbstractScalarArgument<BigInteger>, BigIntegerArgument {
  constructor(default: BigInteger, formatter: ValueFormatter<BigInteger>)
    : super(BigInteger.ZERO, default, true, formatter)

  constructor(default: BigInteger)
    : super(BigInteger.ZERO, default, true, ValueFormatter(BigInteger::toString))

  constructor(formatter: ValueFormatter<BigInteger>)
    : super(BigInteger.ZERO, BigInteger.ZERO, false, formatter)

  constructor()
    : super(BigInteger.ZERO, BigInteger.ZERO, false, ValueFormatter(BigInteger::toString))
}