package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

internal class UnconfiguredFlagFilter : FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?> {
  override fun shouldInclude(
    flag:      Flag<Argument<Any?>, Any?>,
    reference: ValueAccessorReference<*, Any?, KCallable<Any?>>?,
    config:    CliSerializationConfig,
  ) = FlagSetFilter.shouldInclude(flag, reference, config)
}
