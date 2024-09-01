package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class ByteArgumentImpl : AbstractScalarArgument<Byte>, ByteArgument {
  constructor(default: Byte, formatter: ValueFormatter<Byte>)
    : super(0, default, true, formatter)

  constructor(default: Byte)
    : super(0, default, true, ValueFormatter(Byte::toString))

  constructor(formatter: ValueFormatter<Byte>)
    : super(0, 0, false, formatter)

  constructor()
    : super(0, 0, false, ValueFormatter(Byte::toString))
}