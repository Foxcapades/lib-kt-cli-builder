package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.CommandSerializer

internal class CommandSerializerImpl(override val config: CliSerializationConfig) : CommandSerializer {
  override fun serializeToIterator(command: Any): Iterator<String> =
    CommandSerializerCore(command, config).serializeToIterator()

  override fun serializeToString(command: Any, preSize: Int): String =
    CommandSerializerCore(command, config).serializeToString(preSize)
}
