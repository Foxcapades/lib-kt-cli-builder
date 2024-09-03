package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object FloatArgs {
  @JvmStatic
  fun required(): FloatArgument
    = FloatArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Float>): FloatArgument
    = FloatArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Float): FloatArgument
    = FloatArgumentImpl(default)

  @JvmStatic
  fun optional(default: Float, formatter: ArgumentFormatter<Float>): FloatArgument
    = FloatArgumentImpl(default, formatter)
}