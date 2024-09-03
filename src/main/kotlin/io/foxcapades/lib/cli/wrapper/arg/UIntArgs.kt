package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UIntArgs {
  @JvmStatic
  fun required(): UIntArgument
    = UIntArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<UInt>): UIntArgument
    = UIntArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UInt): UIntArgument
    = UIntArgumentImpl(default)

  @JvmStatic
  fun optional(default: UInt, formatter: ArgumentFormatter<UInt>): UIntArgument
    = UIntArgumentImpl(default, formatter)
}