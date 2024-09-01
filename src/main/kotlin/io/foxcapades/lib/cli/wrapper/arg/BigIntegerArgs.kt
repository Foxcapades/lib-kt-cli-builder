package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.BigIntegerArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigInteger

object BigIntegerArgs {
  fun required(): BigIntegerArgument
    = BigIntegerArgumentImpl()

  fun required(formatter: ValueFormatter<BigInteger>): BigIntegerArgument
    = BigIntegerArgumentImpl(formatter)

  fun optional(default: BigInteger): BigIntegerArgument
    = BigIntegerArgumentImpl(default)

  fun optional(default: BigInteger, formatter: ValueFormatter<BigInteger>): BigIntegerArgument
    = BigIntegerArgumentImpl(default, formatter)
}