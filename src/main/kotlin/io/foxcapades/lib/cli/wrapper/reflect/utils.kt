@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.wrapper.reflect

import io.foxcapades.lib.cli.wrapper.util.takeAs
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSuperclassOf

internal inline fun KProperty<*>.hasAnnotation(type: KClass<out Annotation>) =
  annotations.any { type.isInstance(it) }

internal inline fun KProperty<*>.findAnnotation(type: KClass<out Annotation>) =
  annotations.find { type.isInstance(it) }

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

internal inline fun KClass<*>.shouldQuote() =
  when (this) {
    Int::class        -> false
    Double::class     -> false
    Long::class       -> false
    Boolean::class    -> false
    BigInteger::class -> false
    BigDecimal::class -> false
    Float::class      -> false
    Byte::class       -> false
    Short::class      -> false
    UInt::class       -> false
    ULong::class      -> false
    UByte::class      -> false
    UShort::class     -> false
    else              -> true
  }

// TODO: wrap this with try catch for bad instantiations
internal inline fun <T : Any> KClass<out T>.getOrCreate() =
  this.objectInstance ?: this.createInstance()
