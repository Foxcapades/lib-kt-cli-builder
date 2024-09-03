package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class UShortArgumentImpl : AbstractScalarArgument<UShort>, UShortArgument {
  constructor(default: UShort, formatter: ArgumentFormatter<UShort>) : super(default, true, false, formatter)

  constructor(default: UShort) : super(default, true, false, ArgumentFormatter(UShort::toString))

  constructor(formatter: ArgumentFormatter<UShort>) : super(0u, false, false, formatter)

  constructor() : super(0u, false, false, ArgumentFormatter(UShort::toString))
}