package io.foxcapades.lib.cli.wrapper.serial

fun interface NullSerializer {
  operator fun invoke(serializationConfig: CliSerializationConfig): String

  companion object {
    @JvmStatic
    fun useEmptyString() = NullSerializer { "''" }

    @JvmStatic
    fun useDigitZero() = NullSerializer { "0" }

    @JvmStatic
    fun useStringNull() = NullSerializer { "null" }

    @JvmStatic
    fun useLiteralText(value: String) = NullSerializer { value }
  }
}

