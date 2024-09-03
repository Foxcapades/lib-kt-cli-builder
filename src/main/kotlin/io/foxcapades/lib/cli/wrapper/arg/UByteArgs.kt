package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UByteArgs {
  @JvmStatic
  fun required(): UByteArgument
    = UByteArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<UByte>): UByteArgument
    = UByteArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UByte): UByteArgument
    = UByteArgumentImpl(default)

  @JvmStatic
  fun optional(default: UByte, formatter: ArgumentFormatter<UByte>): UByteArgument
    = UByteArgumentImpl(default, formatter)
}