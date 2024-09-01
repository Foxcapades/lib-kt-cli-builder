package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.DoubleArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object DoubleArgs {
  @JvmStatic
  fun required(): DoubleArgument
    = DoubleArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<Double>): DoubleArgument
    = DoubleArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Double): DoubleArgument
    = DoubleArgumentImpl(default)

  @JvmStatic
  fun optional(default: Double, formatter: ValueFormatter<Double>): DoubleArgument
    = DoubleArgumentImpl(default, formatter)
}