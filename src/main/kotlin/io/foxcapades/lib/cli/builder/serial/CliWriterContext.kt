package io.foxcapades.lib.cli.builder.serial

interface CliWriterContext<T : Any, V> {
  val config: CliSerializationConfig
}
