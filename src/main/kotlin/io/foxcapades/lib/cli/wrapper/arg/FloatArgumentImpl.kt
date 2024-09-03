package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class FloatArgumentImpl : AbstractScalarArgument<Float>, FloatArgument {
  constructor(default: Float, formatter: ArgumentFormatter<Float>)
    : super(default, true, false, formatter)

  constructor(default: Float)
    : super(default, true, false, ArgumentFormatter(Float::toString))

  constructor(formatter: ArgumentFormatter<Float>)
    : super(0F, false, false, formatter)

  constructor()
    : super(0F, false, false, ArgumentFormatter(Float::toString))
}