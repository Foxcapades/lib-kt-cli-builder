package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.UByteArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UByteArgs {
  @JvmStatic
  fun required(): UByteArgument
    = UByteArgumentImpl()

  @JvmStatic
  fun required(formatter: ValueFormatter<UByte>): UByteArgument
    = UByteArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: UByte): UByteArgument
    = UByteArgumentImpl(default)

  @JvmStatic
  fun optional(default: UByte, formatter: ValueFormatter<UByte>): UByteArgument
    = UByteArgumentImpl(default, formatter)
}