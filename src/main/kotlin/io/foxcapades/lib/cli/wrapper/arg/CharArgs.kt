package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object CharArgs {
  @JvmStatic
  fun required(): CharArgument
    = CharArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Char>): CharArgument
    = CharArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Char): CharArgument
    = CharArgumentImpl(default)

  @JvmStatic
  fun optional(default: Char, formatter: ArgumentFormatter<Char>): CharArgument
    = CharArgumentImpl(default, formatter)
}