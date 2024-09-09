package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

fun interface FlagPredicate<F : Flag<A, V>, A : Argument<V>, V> {
  fun shouldInclude(
    flag:      F,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
    config:    CliSerializationConfig
  ): Boolean
}
