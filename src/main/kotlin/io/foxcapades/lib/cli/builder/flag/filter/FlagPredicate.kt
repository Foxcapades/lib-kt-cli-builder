package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

fun interface FlagPredicate<V> {
  fun shouldInclude(flag: Flag<V>, config: CliSerializationConfig, source: ValueSource): Boolean
}
