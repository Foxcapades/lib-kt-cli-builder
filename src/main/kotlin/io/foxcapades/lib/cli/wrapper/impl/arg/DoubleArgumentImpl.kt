package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class DoubleArgumentImpl : AbstractScalarArgument<Double>, DoubleArgument {
  constructor(default: Double, formatter: ValueFormatter<Double>)
    : super(0.0, default, true, formatter)

  constructor(default: Double)
    : super(0.0, default, true, ValueFormatter(Double::toString))

  constructor(formatter: ValueFormatter<Double>)
    : super(0.0, 0.0, false, formatter)

  constructor()
    : super(0.0, 0.0, false, ValueFormatter(Double::toString))
}