package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.ShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ShortArgs {
  @JvmStatic
  fun required(): ShortArgument
    = ShortArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Short>): ShortArgument
    = ShortArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Short): ShortArgument
    = ShortArgumentImpl(default)

  @JvmStatic
  fun optional(default: Short, formatter: ValueFormatter<Short>): ShortArgument
    = ShortArgumentImpl(default, formatter)
}