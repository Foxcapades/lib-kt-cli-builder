package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

fun interface NullSerializer : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, builder: CliArgumentAppender) = formatValue(builder)

  fun formatValue(builder: CliArgumentAppender)

  operator fun invoke(builder: CliArgumentAppender) = formatValue(builder)

  companion object {
    @JvmStatic
    fun useEmptyString() = NullSerializer { it.appendString("") }

    @JvmStatic
    fun useDigitZero() = NullSerializer { it.appendChar('0') }

    @JvmStatic
    fun useStringNull() = NullSerializer { it.appendString("null") }

    @JvmStatic
    fun useLiteralText(value: String) = NullSerializer { it.appendString(value) }
  }
}

