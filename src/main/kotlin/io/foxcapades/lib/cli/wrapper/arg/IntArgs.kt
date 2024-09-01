package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.IntArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object IntArgs {
  @JvmStatic
  fun required(): IntArgument
    = IntArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Int>): IntArgument
    = IntArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Int): IntArgument
    = IntArgumentImpl(default)

  @JvmStatic
  fun optional(default: Int, formatter: ValueFormatter<Int>): IntArgument
    = IntArgumentImpl(default, formatter)
}