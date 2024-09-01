package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.CharArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object CharArgs {
  @JvmStatic
  fun required(): CharArgument
    = CharArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Char>): CharArgument
    = CharArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Char): CharArgument
    = CharArgumentImpl(default)

  @JvmStatic
  fun optional(default: Char, formatter: ValueFormatter<Char>): CharArgument
    = CharArgumentImpl(default, formatter)
}