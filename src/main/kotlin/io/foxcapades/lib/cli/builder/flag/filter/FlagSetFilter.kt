package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import kotlin.reflect.KCallable

internal object FlagSetFilter : FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?> {
  override fun shouldInclude(
    flag: Flag<Argument<Any?>, Any?>,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
    config: CliSerializationConfig,
  ) = flag.argument.isSet
}
