package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class ShortArgumentImpl : AbstractScalarArgument<Short>, ShortArgument {
  constructor(default: Short, formatter: ValueFormatter<Short>)
    : super(0, default, true, formatter)

  constructor(default: Short)
    : super(0, default, true, ValueFormatter(Short::toString))

  constructor(formatter: ValueFormatter<Short>)
    : super(0, 0, false, formatter)

  constructor()
    : super(0, 0, false, ValueFormatter(Short::toString))
}