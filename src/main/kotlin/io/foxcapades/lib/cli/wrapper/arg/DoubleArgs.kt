package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object DoubleArgs {
  @JvmStatic
  fun required(): DoubleArgument
    = DoubleArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Double>): DoubleArgument
    = DoubleArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Double): DoubleArgument
    = DoubleArgumentImpl(default)

  @JvmStatic
  fun optional(default: Double, formatter: ArgumentFormatter<Double>): DoubleArgument
    = DoubleArgumentImpl(default, formatter)
}