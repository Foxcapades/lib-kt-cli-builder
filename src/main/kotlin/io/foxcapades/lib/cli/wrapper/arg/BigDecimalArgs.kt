package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import java.math.BigDecimal

object BigDecimalArgs {
  fun required(): BigDecimalArgument
    = BigDecimalArgumentImpl()

  fun required(formatter: ArgumentFormatter<BigDecimal>): BigDecimalArgument
    = BigDecimalArgumentImpl(formatter)

  fun optional(default: BigDecimal): BigDecimalArgument
    = BigDecimalArgumentImpl(default)

  fun optional(default: BigDecimal, formatter: ArgumentFormatter<BigDecimal>): BigDecimalArgument
    = BigDecimalArgumentImpl(default, formatter)
}
