package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class UIntArgumentImpl : AbstractScalarArgument<UInt>, UIntArgument {
  constructor(default: UInt, formatter: ArgumentFormatter<UInt>) : super(default, true, false, formatter)

  constructor(default: UInt) : super(default, true, false, ArgumentFormatter(UInt::toString))

  constructor(formatter: ArgumentFormatter<UInt>) : super(0u, false, false, formatter)

  constructor() : super(0u, false, false, ArgumentFormatter(UInt::toString))
}