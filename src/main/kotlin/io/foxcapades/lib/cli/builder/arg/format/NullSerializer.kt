package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

fun interface NullSerializer : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, writer: CliArgumentWriter<*, Any?>) = formatValue(writer)

  fun formatValue(builder: CliArgumentWriter<*, *>)

  companion object {
    @JvmStatic
    fun useEmptyString() = NullSerializer { it.writeString("") }

    @JvmStatic
    fun useDigitZero() = NullSerializer { it.writeChar('0') }

    @JvmStatic
    fun useStringNull() = NullSerializer { it.writeString("null") }

    @JvmStatic
    fun useLiteralText(value: String) = NullSerializer { it.writeString(value) }
  }
}

