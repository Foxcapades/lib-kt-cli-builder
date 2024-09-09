@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KParameter

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> KFunction1<*, *>.unsafeCast() =
  this as KFunction1<T, V>

internal inline fun KFunction1<*, *>.forceAny() =
  unsafeCast<Any, Any?>()

internal inline val KFunction1<*, *>.isGetter
  get() = parameters.size == 1 && parameters[0].kind == KParameter.Kind.INSTANCE

internal inline fun KFunction1<*, *>.qualifiedName(parent: KClass<*>) =
  parent.qualifiedName + "::" + name + "(...)"
