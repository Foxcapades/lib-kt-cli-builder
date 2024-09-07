package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

object NonNullGeneralStringifier : ArgumentFormatter<Any> {
  override fun formatValue(value: Any, writer: CliArgumentWriter<*, Any>) {
    writer.writeString(value.toString())
  }
}
