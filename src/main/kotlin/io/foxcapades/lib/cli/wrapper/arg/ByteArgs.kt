package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ByteArgs {
  @JvmStatic
  fun required(): ByteArgument
    = ByteArgumentImpl()

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Byte>): ByteArgument
    = ByteArgumentImpl(formatter)

  @JvmStatic
  fun optional(default: Byte): ByteArgument
    = ByteArgumentImpl(default)

  @JvmStatic
  fun optional(default: Byte, formatter: ArgumentFormatter<Byte>): ByteArgument
    = ByteArgumentImpl(default, formatter)

  @JvmStatic
  fun formatASCII(value: Byte): String
    = ByteArray(1) { value }.decodeToString()
}