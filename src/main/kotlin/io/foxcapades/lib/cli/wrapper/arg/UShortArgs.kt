package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UShortArgs {
  @JvmStatic
  fun required(): UShortArgument
    = UShortArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<UShort>): UShortArgument
    = UShortArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UShort): UShortArgument
    = UShortArgumentImpl(default)

  @JvmStatic
  fun optional(default: UShort, formatter: ArgumentFormatter<UShort>): UShortArgument
    = UShortArgumentImpl(default, formatter)
}