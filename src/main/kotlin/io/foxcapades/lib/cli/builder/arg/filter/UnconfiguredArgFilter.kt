package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

internal class UnconfiguredArgFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, Any?, KCallable<Any?>>?,
  ) = ArgSetFilter.shouldInclude(argument, config, reference)
}
