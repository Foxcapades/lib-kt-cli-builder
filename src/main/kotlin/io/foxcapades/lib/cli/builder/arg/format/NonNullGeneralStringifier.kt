package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

internal object NonNullGeneralStringifier : ArgumentFormatter<Any> {
  override fun formatValue(value: Any, writer: CliArgumentWriter<*, Any>, reference: Argument<Any>) {
    writer.writeString(value.toString())
  }
}
