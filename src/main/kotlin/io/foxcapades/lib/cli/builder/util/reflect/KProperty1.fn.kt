@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.kt.prop.delegation.MutableProperty
import io.foxcapades.lib.cli.builder.util.takeAs
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.cast
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.jvm.isAccessible


@Suppress("UNCHECKED_CAST")
internal inline fun <T> KProperty1<*, *>.property(instance: Any) =
  (this as KProperty1<Any, *>)
    .apply { isAccessible = true }
    .getDelegate(instance) as MutableProperty<T>

@Suppress("UNCHECKED_CAST")
internal inline fun <R : Any> KProperty1<*, *>.asDelegateType(instance: Any, delegateType: KClass<R>): R? =
  // If the raw return type of the property is a subclass of [delegateType] then
  // the property isn't a delegate, it's a raw component value
  if (returnType.classifier?.takeAs<KClass<*>>()?.let(delegateType::isSuperclassOf) == true)
    forceAny().get(instance) as R
  // else, it is not a raw component value, try and see if there really is a
  // delegate backing the property
  else
    forceAny().getDelegate(instance)
      ?.takeIf { delegateType.isInstance(it) }
      ?.let { delegateType.cast(it) }

internal inline fun <reified R : Any> KProperty1<*, *>.asDelegateType(instance: Any): R? =
  asDelegateType(instance, R::class)

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> KProperty1<*, *>.unsafeCast() = this as KProperty1<T, V>

@Suppress("UNCHECKED_CAST")
internal inline fun KProperty1<*, *>.forceAny() = this as KProperty1<Any, Any?>
