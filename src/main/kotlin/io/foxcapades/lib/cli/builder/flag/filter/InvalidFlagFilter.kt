package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import kotlin.reflect.KCallable

internal object InvalidFlagFilter : FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?> {
  override fun shouldInclude(
    flag: Flag<Argument<Any?>, Any?>,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
    config: CliSerializationConfig,
  ) = BUG()
}
