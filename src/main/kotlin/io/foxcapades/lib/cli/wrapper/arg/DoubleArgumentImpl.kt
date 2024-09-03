package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class DoubleArgumentImpl : AbstractScalarArgument<Double>, DoubleArgument {
  constructor(default: Double, formatter: ArgumentFormatter<Double>)
    : super(default, true, false, formatter)

  constructor(default: Double)
    : super(default, true, false, ArgumentFormatter(Double::toString))

  constructor(formatter: ArgumentFormatter<Double>)
    : super(0.0, false, false, formatter)

  constructor()
    : super(0.0, false, false, ArgumentFormatter(Double::toString))
}