package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class ULongArgumentImpl : AbstractScalarArgument<ULong>, ULongArgument {
  constructor(default: ULong, formatter: ValueFormatter<ULong>) : super(0u, default, true, formatter)

  constructor(default: ULong) : super(0u, default, true, ValueFormatter(ULong::toString))

  constructor(formatter: ValueFormatter<ULong>) : super(0u, 0u, false, formatter)

  constructor() : super(0u, 0u, false, ValueFormatter(ULong::toString))
}