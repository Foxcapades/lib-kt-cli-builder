package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object NullableGeneralStringifier : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, builder: CliArgumentAppender) {
    if (value == null)
      builder.config.nullSerializer(builder)
    else
      builder.appendString(value.toString())
  }
}
