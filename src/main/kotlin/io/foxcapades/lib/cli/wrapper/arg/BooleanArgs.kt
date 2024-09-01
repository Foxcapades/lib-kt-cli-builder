package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.BooleanArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object BooleanArgs {
  @JvmStatic
  fun required(): BooleanArgument
    = BooleanArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Boolean>): BooleanArgument
    = BooleanArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Boolean): BooleanArgument
    = BooleanArgumentImpl(default)

  @JvmStatic
  fun optional(default: Boolean, formatter: ValueFormatter<Boolean>): BooleanArgument
    = BooleanArgumentImpl(default, formatter)

  @JvmStatic
  fun formatYesNo(value: Boolean)
    = if (value) "yes" else "no"

  @JvmStatic
  fun formatBinary(value: Boolean)
    = if (value) "1" else "0"
}