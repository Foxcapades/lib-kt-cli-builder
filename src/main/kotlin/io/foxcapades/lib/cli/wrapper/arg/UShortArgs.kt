package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.UShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UShortArgs {
  @JvmStatic
  fun required(): UShortArgument
    = UShortArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<UShort>): UShortArgument
    = UShortArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UShort): UShortArgument
    = UShortArgumentImpl(default)

  @JvmStatic
  fun optional(default: UShort, formatter: ValueFormatter<UShort>): UShortArgument
    = UShortArgumentImpl(default, formatter)
}