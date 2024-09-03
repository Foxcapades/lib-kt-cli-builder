package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class ShortArgumentImpl : AbstractScalarArgument<Short>, ShortArgument {
  constructor(default: Short, formatter: ArgumentFormatter<Short>)
    : super(default, true, false, formatter)

  constructor(default: Short)
    : super(default, true, false, ArgumentFormatter(Short::toString))

  constructor(formatter: ArgumentFormatter<Short>)
    : super(0, false, false, formatter)

  constructor()
    : super(0, false, false, ArgumentFormatter(Short::toString))
}