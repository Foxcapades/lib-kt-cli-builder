package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object NullableGeneralStringifier : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, builder: CliArgumentWriter<*, Any?>) {
    if (value == null)
      builder.config.nullSerializer.formatValue(builder)
    else
      builder.writeString(value.toString())
  }
}
