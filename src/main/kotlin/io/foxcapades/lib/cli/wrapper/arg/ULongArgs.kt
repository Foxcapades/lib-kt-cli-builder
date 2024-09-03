package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ULongArgs {
  @JvmStatic
  fun required(): ULongArgument
    = ULongArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<ULong>): ULongArgument
    = ULongArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: ULong): ULongArgument
    = ULongArgumentImpl(default)

  @JvmStatic
  fun optional(default: ULong, formatter: ArgumentFormatter<ULong>): ULongArgument
    = ULongArgumentImpl(default, formatter)
}