package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import java.math.BigDecimal

internal class BigDecimalArgumentImpl : AbstractScalarArgument<BigDecimal>, BigDecimalArgument {
  constructor(
    default: Property<BigDecimal>,
    shouldQuote: Property<Boolean>,
    formatter: Property<ArgumentFormatter<BigDecimal>>
  ) : super(
    default     = default.getOr(BigDecimal.ZERO),
    hasDefault  = default.isSet,
    shouldQuote = shouldQuote.getOr(false),
    formatter.getOr(ArgumentFormatter(BigDecimal::toPlainString))
  )

  constructor(default: BigDecimal, formatter: ArgumentFormatter<BigDecimal>)
    : super(default, true, false, formatter)

  constructor(default: BigDecimal)
    : super(default, true, false, ArgumentFormatter(BigDecimal::toPlainString))

  constructor(formatter: ArgumentFormatter<BigDecimal>)
    : super(BigDecimal.ZERO, false, false, formatter)

  constructor()
    : super(BigDecimal.ZERO, false, false, ArgumentFormatter(BigDecimal::toPlainString))
}
