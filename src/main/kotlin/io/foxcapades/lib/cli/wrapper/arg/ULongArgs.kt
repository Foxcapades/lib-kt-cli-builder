package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.ULongArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ULongArgs {
  @JvmStatic
  fun required(): ULongArgument
    = ULongArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<ULong>): ULongArgument
    = ULongArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: ULong): ULongArgument
    = ULongArgumentImpl(default)

  @JvmStatic
  fun optional(default: ULong, formatter: ValueFormatter<ULong>): ULongArgument
    = ULongArgumentImpl(default, formatter)
}