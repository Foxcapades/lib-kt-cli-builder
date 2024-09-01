package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.StringArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object StringArgs {
  @JvmStatic
  fun required(): StringArgument
    = StringArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<String>): StringArgument
    = StringArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: String): StringArgument
    = StringArgumentImpl(default)

  @JvmStatic
  fun optional(default: String, formatter: ValueFormatter<String>): StringArgument
    = StringArgumentImpl(default, formatter)
}