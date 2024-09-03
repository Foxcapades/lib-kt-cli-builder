package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class ByteArgumentImpl : AbstractScalarArgument<Byte>, ByteArgument {
  constructor(default: Byte, formatter: ArgumentFormatter<Byte>)
    : super(default, true, false, formatter)

  constructor(default: Byte)
    : super(default, true, false, ArgumentFormatter(Byte::toString))

  constructor(formatter: ArgumentFormatter<Byte>)
    : super(0, false, false, formatter)

  constructor()
    : super(0, false, false, ArgumentFormatter(Byte::toString))
}