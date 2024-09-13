package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal object FlagSetFilter : FlagPredicate<Any?> {
  override fun shouldInclude(flag: Flag<Any?>, config: CliSerializationConfig, source: ValueSource) =
    flag.argument.shouldSerialize(config, source)
}
