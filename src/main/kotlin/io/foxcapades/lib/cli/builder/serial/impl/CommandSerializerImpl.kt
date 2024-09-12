package io.foxcapades.lib.cli.builder.serial.impl

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.CommandSerializer

@JvmInline
internal value class CommandSerializerImpl(override val config: CliSerializationConfig) : CommandSerializer {
  override fun serializeToIterator(command: Any): Iterator<String> =
    LazyCliAppenderImpl(ResolvedCommand(command, null, null), config)

  override fun serializeToString(command: Any, preSize: Int): String =
    StringCliAppenderImpl(ResolvedCommand(command, null, null), config, preSize).toString()
}
