package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.FloatArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class FloatArgumentImpl : AbstractScalarArgument<Float>, FloatArgument {
  constructor(default: Float, formatter: ValueFormatter<Float>)
    : super(0F, default, true, formatter)

  constructor(default: Float)
    : super(0F, default, true, ValueFormatter(Float::toString))

  constructor(formatter: ValueFormatter<Float>)
    : super(0F, 0F, false, formatter)

  constructor()
    : super(0F, 0F, false, ValueFormatter(Float::toString))
}