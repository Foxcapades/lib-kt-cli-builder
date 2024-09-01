package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig

fun interface ValueFormatter<T> {
  operator fun invoke(value: T, serializationConfig: CliSerializationConfig): String

  companion object {
    @JvmStatic
    fun <T: Any> ofToString() = ValueFormatter<T> { v, _ -> v.toString() }

    @JvmStatic
    fun <T> simple(fn: (T) -> String) = ValueFormatter<T> { v, _ -> fn(v) }
  }
}
