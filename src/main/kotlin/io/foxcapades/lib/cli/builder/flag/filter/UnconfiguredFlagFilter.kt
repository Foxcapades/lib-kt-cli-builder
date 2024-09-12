package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class UnconfiguredFlagFilter : FlagPredicate<Any?> {
  override fun shouldInclude(flag: Flag<Any?>, config: CliSerializationConfig, source: ValueSource) =
    FlagSetFilter.shouldInclude(flag, config, source)
}
