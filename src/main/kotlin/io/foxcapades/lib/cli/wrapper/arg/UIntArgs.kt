package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.UIntArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UIntArgs {
  @JvmStatic
  fun required(): UIntArgument
    = UIntArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<UInt>): UIntArgument
    = UIntArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UInt): UIntArgument
    = UIntArgumentImpl(default)

  @JvmStatic
  fun optional(default: UInt, formatter: ValueFormatter<UInt>): UIntArgument
    = UIntArgumentImpl(default, formatter)
}