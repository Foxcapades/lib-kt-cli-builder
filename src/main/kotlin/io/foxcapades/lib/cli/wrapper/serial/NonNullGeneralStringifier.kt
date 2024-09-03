package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object NonNullGeneralStringifier : ArgumentFormatter<Any> {
  override fun formatValue(value: Any, builder: CliArgumentAppender) {
    builder.appendString(value.toString())
  }
}
