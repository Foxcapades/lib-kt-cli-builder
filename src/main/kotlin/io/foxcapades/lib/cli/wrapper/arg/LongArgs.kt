package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.LongArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object LongArgs {
  @JvmStatic
  fun required(): LongArgument
    = LongArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Long>): LongArgument
    = LongArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Long): LongArgument
    = LongArgumentImpl(default)

  @JvmStatic
  fun optional(default: Long, formatter: ValueFormatter<Long>): LongArgument
    = LongArgumentImpl(default, formatter)
}