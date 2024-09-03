package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class LongArgumentImpl : AbstractScalarArgument<Long>, LongArgument {
  constructor(default: Long, formatter: ArgumentFormatter<Long>)
    : super(default, true, false, formatter)

  constructor(default: Long)
    : super(default, true, false, ArgumentFormatter(Long::toString))

  constructor(formatter: ArgumentFormatter<Long>)
    : super(0, false, false, formatter)

  constructor()
    : super(0, false, false, ArgumentFormatter(Long::toString))
}