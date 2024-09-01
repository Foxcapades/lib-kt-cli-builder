package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.IntArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class IntArgumentImpl : AbstractScalarArgument<Int>, IntArgument {
  constructor(default: Int, formatter: ValueFormatter<Int>)
    : super(0, default, true, formatter)

  constructor(default: Int)
    : super(0, default, true, ValueFormatter(Int::toString))

  constructor(formatter: ValueFormatter<Int>)
    : super(0, 0, false, formatter)

  constructor()
    : super(0, 0, false, ValueFormatter(Int::toString))
}