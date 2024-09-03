package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object StringArgs {
  @JvmStatic
  fun required(): StringArgument
    = StringArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<String>): StringArgument
    = StringArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: String): StringArgument
    = StringArgumentImpl(default)

  @JvmStatic
  fun optional(default: String, formatter: ArgumentFormatter<String>): StringArgument
    = StringArgumentImpl(default, formatter)
}