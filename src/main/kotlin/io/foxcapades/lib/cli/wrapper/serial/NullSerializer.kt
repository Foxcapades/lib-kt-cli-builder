package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

fun interface NullSerializer : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, builder: CliArgumentWriter<*, Any?>) = formatValue(builder)

  fun formatValue(builder: CliArgumentWriter<*, Any?>)

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

