package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

class NullableGeneralStringifier : ValueFormatter<Any?> {
  override fun invoke(value: Any?, serializationConfig: CliSerializationConfig) =
    value?.toString() ?: serializationConfig.nullSerializer(serializationConfig)
}

