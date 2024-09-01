package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

class NonNullGeneralStringifier : ValueFormatter<Any> {
  override fun invoke(value: Any, serializationConfig: CliSerializationConfig) =
    value.toString()
}