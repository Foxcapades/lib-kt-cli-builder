package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.CharArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class CharArgumentImpl : AbstractScalarArgument<Char>, CharArgument {
  constructor(default: Char, formatter: ValueFormatter<Char>)
    : super('\u0000', default, true, formatter)

  constructor(default: Char)
    : super('\u0000', default, true, ValueFormatter(Char::toString))

  constructor(formatter: ValueFormatter<Char>)
    : super('\u0000', '\u0000', false, formatter)

  constructor()
    : super('\u0000', '\u0000', false, ValueFormatter(Char::toString))
}