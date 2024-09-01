package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class BooleanArgumentImpl : AbstractScalarArgument<Boolean>, BooleanArgument {
  constructor(default: Boolean, formatter: ValueFormatter<Boolean>)
    : super(false, default, true, formatter)

  constructor(default: Boolean)
    : super(false, default, true, ValueFormatter(Boolean::toString))

  constructor(formatter: ValueFormatter<Boolean>)
    : super(false, false, false, formatter)

  constructor()
    : super(false, false, false, ValueFormatter(Boolean::toString))
}