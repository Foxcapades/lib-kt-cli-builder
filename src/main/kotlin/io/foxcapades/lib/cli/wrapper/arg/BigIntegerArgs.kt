package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import java.math.BigInteger

object BigIntegerArgs {
  fun required(): BigIntegerArgument
    = BigIntegerArgumentImpl()

  fun required(formatter: ArgumentFormatter<BigInteger>): BigIntegerArgument
    = BigIntegerArgumentImpl(formatter)

  fun optional(default: BigInteger): BigIntegerArgument
    = BigIntegerArgumentImpl(default)

  fun optional(default: BigInteger, formatter: ArgumentFormatter<BigInteger>): BigIntegerArgument
    = BigIntegerArgumentImpl(default, formatter)
}