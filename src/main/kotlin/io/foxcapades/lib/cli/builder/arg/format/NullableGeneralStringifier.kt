package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

object NullableGeneralStringifier : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, builder: CliArgumentWriter<*, Any?>) {
    if (value == null)
      builder.config.nullSerializer.formatValue(builder)
    else
      builder.writeString(value.toString())
  }
}
