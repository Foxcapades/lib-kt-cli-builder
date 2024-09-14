@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KFunction0

@Suppress("UNCHECKED_CAST")
internal inline fun <V> KFunction0<*>.unsafeCast() = this as KFunction0<V>
