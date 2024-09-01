package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class UByteArgumentImpl : AbstractScalarArgument<UByte>, UByteArgument {
  constructor(default: UByte, formatter: ValueFormatter<UByte>) : super(0u, default, true, formatter)

  constructor(default: UByte) : super(0u, default, true, ValueFormatter(UByte::toString))

  constructor(formatter: ValueFormatter<UByte>) : super(0u, 0u, false, formatter)

  constructor() : super(0u, 0u, false, ValueFormatter(UByte::toString))
}