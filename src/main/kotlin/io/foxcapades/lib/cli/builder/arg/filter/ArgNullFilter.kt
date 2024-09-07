package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import kotlin.reflect.KCallable

internal object ArgNullFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
    config: CliSerializationConfig,
  ) = argument.isSet && argument.get() != null
}
