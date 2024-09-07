package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <F : Flag<A, V>, A : Argument<V>, V> FlagPredicate<*, *, *>.unsafeCast() =
  this as FlagPredicate<F, A, V>
