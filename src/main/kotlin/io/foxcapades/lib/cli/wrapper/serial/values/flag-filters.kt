package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.BUG

fun interface FlagPredicate<F : Flag<A, V>, A : Argument<V>, V> {
  fun shouldInclude(
    flag: F,
    reference: ResolvedFlag<*, V>,
    config: CliSerializationConfig
  ): Boolean
}

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <F : Flag<A, V>, A : Argument<V>, V> FlagPredicate<*, *, *>.unsafeCast() =
  this as FlagPredicate<F, A, V>


internal object FlagSetFilter : FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?> {
  override fun shouldInclude(
    flag: Flag<Argument<Any?>, Any?>,
    reference: ResolvedFlag<*, Any?>,
    config: CliSerializationConfig,
  ) = flag.argument.isSet
}

internal object InvalidFlagFilter : FlagPredicate<Flag<Argument<Any?>, Any?>, Argument<Any?>, Any?> {
  override fun shouldInclude(
    flag: Flag<Argument<Any?>, Any?>,
    reference: ResolvedFlag<*, Any?>,
    config: CliSerializationConfig,
  ) = BUG()
}
