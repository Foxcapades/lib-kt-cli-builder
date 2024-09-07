@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.reflect

import io.foxcapades.lib.cli.builder.util.properties.MutableProperty
import io.foxcapades.lib.cli.builder.util.takeAs
import kotlin.reflect.*
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.jvm.isAccessible


@Suppress("UNCHECKED_CAST")
internal inline fun <T> KProperty1<*, *>.property(instance: Any) =
  (this as KProperty1<Any, *>)
    .apply { isAccessible = true }
    .getDelegate(instance) as MutableProperty<T>

@Suppress("UNCHECKED_CAST")
internal inline fun <T, R : Any> KProperty1<T, *>.asDelegateType(instance: T, delegateType: KClass<R>): R? {
  return if (returnType.classifier?.takeAs<KClassifier, KClass<*>>()?.let(delegateType::isSuperclassOf) == true)
    get(instance) as R?
  else
    getDelegate(instance)
      ?.takeIf { delegateType.isInstance(it) }
      ?.let { delegateType.cast(it) }
}

internal inline fun <T, reified R : Any> KProperty1<T, *>.asDelegateType(instance: T) =
  asDelegateType(instance, R::class)

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> KProperty1<*, *>.unsafeCast() = this as KProperty1<T, V>

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> KFunction1<*, *>.unsafeCast() = this as KFunction1<T, V>
