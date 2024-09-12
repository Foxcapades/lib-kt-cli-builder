@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag.filter

import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal inline fun <V> FlagPredicate<*>.unsafeCast() =
  this as FlagPredicate<V>

internal inline fun <V> KClass<out FlagPredicate<*>>.new() =
  getOrCreate().unsafeCast<V>()

