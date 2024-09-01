package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.BigDecimalArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigDecimal

object BigDecimalArgs {
  fun required(): BigDecimalArgument
    = BigDecimalArgumentImpl()

  fun required(formatter: ValueFormatter<BigDecimal>): BigDecimalArgument
    = BigDecimalArgumentImpl(formatter)

  fun optional(default: BigDecimal): BigDecimalArgument
    = BigDecimalArgumentImpl(default)

  fun optional(default: BigDecimal, formatter: ValueFormatter<BigDecimal>): BigDecimalArgument
    = BigDecimalArgumentImpl(default, formatter)
}