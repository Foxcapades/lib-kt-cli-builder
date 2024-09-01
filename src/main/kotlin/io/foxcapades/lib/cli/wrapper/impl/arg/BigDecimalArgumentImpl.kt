package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigDecimal

internal class BigDecimalArgumentImpl : AbstractScalarArgument<BigDecimal>, BigDecimalArgument {
  constructor(default: BigDecimal, formatter: ValueFormatter<BigDecimal>)
    : super(BigDecimal.ZERO, default, true, formatter)

  constructor(default: BigDecimal)
    : super(BigDecimal.ZERO, default, true, ValueFormatter(BigDecimal::toPlainString))

  constructor(formatter: ValueFormatter<BigDecimal>)
    : super(BigDecimal.ZERO, BigDecimal.ZERO, false, formatter)

  constructor()
    : super(BigDecimal.ZERO, BigDecimal.ZERO, false, ValueFormatter(BigDecimal::toPlainString))
}