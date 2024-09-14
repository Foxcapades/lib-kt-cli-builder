@file:JvmName("InternalFlagExtensions")
@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag


@Suppress("UNCHECKED_CAST")
internal inline fun <V> Flag<*>.unsafeCast() = this as Flag<V>


internal inline fun Flag<*>.forceAny() = unsafeCast<Any?>()
