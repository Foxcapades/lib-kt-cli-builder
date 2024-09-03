package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class CharArgumentImpl : AbstractScalarArgument<Char>, CharArgument {
  constructor(default: Char, formatter: ArgumentFormatter<Char>)
    : super(default, true, false, formatter)

  constructor(default: Char)
    : super(default, true, false, ArgumentFormatter(Char::toString))

  constructor(formatter: ArgumentFormatter<Char>)
    : super('\u0000', false, false, formatter)

  constructor()
    : super('\u0000', false, false, ArgumentFormatter(Char::toString))
}