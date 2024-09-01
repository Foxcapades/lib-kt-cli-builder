package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.FloatArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object FloatArgs {
  @JvmStatic
  fun required(): FloatArgument
    = FloatArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Float>): FloatArgument
    = FloatArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Float): FloatArgument
    = FloatArgumentImpl(default)

  @JvmStatic
  fun optional(default: Float, formatter: ValueFormatter<Float>): FloatArgument
    = FloatArgumentImpl(default, formatter)
}