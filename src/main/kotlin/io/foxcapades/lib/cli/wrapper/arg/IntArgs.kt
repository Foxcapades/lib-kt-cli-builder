package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object IntArgs {
  @JvmStatic
  fun required(): IntArgument
    = IntArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Int>): IntArgument
    = IntArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Int): IntArgument
    = IntArgumentImpl(default)

  @JvmStatic
  fun optional(default: Int, formatter: ArgumentFormatter<Int>): IntArgument
    = IntArgumentImpl(default, formatter)
}