package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object LongArgs {
  @JvmStatic
  fun required(): LongArgument
    = LongArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Long>): LongArgument
    = LongArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Long): LongArgument
    = LongArgumentImpl(default)

  @JvmStatic
  fun optional(default: Long, formatter: ArgumentFormatter<Long>): LongArgument
    = LongArgumentImpl(default, formatter)
}