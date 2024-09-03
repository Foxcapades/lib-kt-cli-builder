package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class StringArgumentImpl : AbstractScalarArgument<String>, StringArgument {
  constructor(default: String, formatter: ArgumentFormatter<String>)
    : super(default, true, true, formatter)

  constructor(default: String)
    : super(default, true, true, ArgumentFormatter(String::toString))

  constructor(formatter: ArgumentFormatter<String>)
    : super("", false, true, formatter)

  constructor()
    : super("", false, true, ArgumentFormatter(String::toString))
}