@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import io.foxcapades.lib.cli.builder.util.properties.MutableProperty
import io.foxcapades.lib.cli.builder.util.takeAs
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
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
internal inline fun <R : Any> KProperty1<*, *>.asDelegateType(instance: Any, delegateType: KClass<R>): R? {
  return if (returnType.classifier?.takeAs<KClassifier, KClass<*>>()?.let(delegateType::isSuperclassOf) == true)
    forceAny().get(instance) as R?
  else
    forceAny().getDelegate(instance)
      ?.takeIf { delegateType.isInstance(it) }
      ?.let { delegateType.cast(it) }
}

internal inline fun <reified R : Any> KProperty1<*, *>.asDelegateType(instance: Any) =
  asDelegateType(instance, R::class)

@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any, V> KProperty1<*, *>.unsafeCast() = this as KProperty1<T, V>

@Suppress("UNCHECKED_CAST")
internal inline fun KProperty1<*, *>.forceAny() = this as KProperty1<Any, Any?>

internal inline fun KProperty1<*, *>.qualifiedName(parent: KClass<*>) =
  parent.qualifiedName + "::" + name

internal inline fun <reified A : Annotation> KProperty1<*, *>.makeDuplicateAnnotationsError(type: KClass<out Any>) =
  makeDuplicateAnnotationsError(type, A::class)

internal inline fun KProperty1<*, *>.makeDuplicateAnnotationsError(parent: KClass<out Any>, type: KClass<out Annotation>) =
  IllegalStateException("${qualifiedName(parent)} has more than one ${type::class.simpleName} annotation")
