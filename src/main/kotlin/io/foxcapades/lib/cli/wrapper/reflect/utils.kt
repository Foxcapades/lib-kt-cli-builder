@file:Suppress("NOTHING_TO_INLINE")

package io.foxcapades.lib.cli.wrapper.reflect

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.cast
import kotlin.reflect.full.createInstance

internal inline fun KProperty<*>.hasAnnotation(type: KClass<out Annotation>) =
  annotations.any { type.isInstance(it) }

internal inline fun KProperty<*>.findAnnotation(type: KClass<out Annotation>) =
  annotations.find { type.isInstance(it) }

internal inline fun <T> KProperty1<T, *>.isDelegateType(value: T, type: KClass<*>) =
  type.isInstance(getDelegate(value))

internal inline fun <T, R : Any> KProperty1<T, *>.asDelegateType(value: T, type: KClass<R>) =
  getDelegate(value)?.takeIf { type.isInstance(it) }?.let { type.cast(it) }

internal inline fun <T, reified R : Any> KProperty1<T, *>.asDelegateType(value: T) =
  asDelegateType(value, R::class)

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