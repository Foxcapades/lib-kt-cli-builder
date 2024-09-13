package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class UnconfiguredArgFilter : ArgumentPredicate<Any?> {
  override fun shouldInclude(argument: Argument<Any?>, config: CliSerializationConfig, source: ValueSource) =
    ArgUnsetFilter.shouldInclude(argument, config, source)
}
