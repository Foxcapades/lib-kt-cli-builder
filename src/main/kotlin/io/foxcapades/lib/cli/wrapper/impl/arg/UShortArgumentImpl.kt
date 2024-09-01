package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.UShortArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class UShortArgumentImpl : AbstractScalarArgument<UShort>, UShortArgument {
  constructor(default: UShort, formatter: ValueFormatter<UShort>) : super(0u, default, true, formatter)

  constructor(default: UShort) : super(0u, default, true, ValueFormatter(UShort::toString))

  constructor(formatter: ValueFormatter<UShort>) : super(0u, 0u, false, formatter)

  constructor() : super(0u, 0u, false, ValueFormatter(UShort::toString))
}