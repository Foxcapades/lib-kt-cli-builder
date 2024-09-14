@file:JvmName("InternalFlagPredicateExtensions")
@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag.filter

@Suppress("UNCHECKED_CAST")
internal inline fun <V> FlagPredicate<*>.unsafeCast() = this as FlagPredicate<V>

