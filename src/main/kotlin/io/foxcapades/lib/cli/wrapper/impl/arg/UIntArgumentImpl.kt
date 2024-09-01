package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class UIntArgumentImpl : AbstractScalarArgument<UInt>, UIntArgument {
  constructor(default: UInt, formatter: ValueFormatter<UInt>) : super(0u, default, true, formatter)

  constructor(default: UInt) : super(0u, default, true, ValueFormatter(UInt::toString))

  constructor(formatter: ValueFormatter<UInt>) : super(0u, 0u, false, formatter)

  constructor() : super(0u, 0u, false, ValueFormatter(UInt::toString))
}