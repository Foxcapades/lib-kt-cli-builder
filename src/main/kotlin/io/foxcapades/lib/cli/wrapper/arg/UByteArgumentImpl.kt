package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class UByteArgumentImpl : AbstractScalarArgument<UByte>, UByteArgument {
  constructor(default: UByte, formatter: ArgumentFormatter<UByte>) : super(default, true, false, formatter)

  constructor(default: UByte) : super(default, true, false, ArgumentFormatter(UByte::toString))

  constructor(formatter: ArgumentFormatter<UByte>) : super(0u, false, false, formatter)

  constructor() : super(0u, false, false, ArgumentFormatter(UByte::toString))
}