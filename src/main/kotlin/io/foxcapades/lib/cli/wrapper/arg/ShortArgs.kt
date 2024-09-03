package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ShortArgs {
  @JvmStatic
  fun required(): ShortArgument
    = ShortArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Short>): ShortArgument
    = ShortArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Short): ShortArgument
    = ShortArgumentImpl(default)

  @JvmStatic
  fun optional(default: Short, formatter: ArgumentFormatter<Short>): ShortArgument
    = ShortArgumentImpl(default, formatter)
}