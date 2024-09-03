package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class ULongArgumentImpl : AbstractScalarArgument<ULong>, ULongArgument {
  constructor(default: ULong, formatter: ArgumentFormatter<ULong>) : super(default, true, false, formatter)

  constructor(default: ULong) : super(default, true, false, ArgumentFormatter(ULong::toString))

  constructor(formatter: ArgumentFormatter<ULong>) : super(0u, false, false, formatter)

  constructor() : super(0u, false, false, ArgumentFormatter(ULong::toString))
}