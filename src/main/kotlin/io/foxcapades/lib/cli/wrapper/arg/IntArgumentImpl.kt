package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class IntArgumentImpl : AbstractScalarArgument<Int>, IntArgument {
  constructor(default: Int, formatter: ArgumentFormatter<Int>)
    : super(default, true, false, formatter)

  constructor(default: Int)
    : super(default, true, false, ArgumentFormatter(Int::toString))

  constructor(formatter: ArgumentFormatter<Int>)
    : super(0, false, false, formatter)

  constructor()
    : super(0, false, false, ArgumentFormatter(Int::toString))
}