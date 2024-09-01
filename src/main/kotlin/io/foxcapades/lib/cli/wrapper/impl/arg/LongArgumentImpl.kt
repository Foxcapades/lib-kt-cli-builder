package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class LongArgumentImpl : AbstractScalarArgument<Long>, LongArgument {
  constructor(default: Long, formatter: ValueFormatter<Long>)
    : super(0, default, true, formatter)

  constructor(default: Long)
    : super(0, default, true, ValueFormatter(Long::toString))

  constructor(formatter: ValueFormatter<Long>)
    : super(0, 0, false, formatter)

  constructor()
    : super(0, 0, false, ValueFormatter(Long::toString))
}