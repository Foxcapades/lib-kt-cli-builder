package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

object NullableGeneralStringifier : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, writer: CliArgumentWriter<*, Any?>, reference: Argument<Any?>) {
    if (value == null)
      writer.config.nullSerializer.formatValue(writer)
    else
      writer.writeString(value.toString())
  }
}
