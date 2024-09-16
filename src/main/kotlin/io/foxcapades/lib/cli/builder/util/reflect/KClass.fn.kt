@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.reflect

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.superclasses


internal inline val KClass<*>.safeName
  get() = qualifiedName ?: simpleName ?: "unknown"


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


@Suppress("UNCHECKED_CAST")
internal inline fun <T : Any> KClass<*>.unsafeCast() = this as KClass<out T>


internal inline fun <reified A : Annotation> KClass<*>.findAnnotations() = sequence {
  val queue = ArrayDeque<KClass<*>>(5).also { it.add(this@findAnnotations) }

  while (queue.isNotEmpty()) {
    val target = queue.removeFirst()

    for (ann in target.annotations) {
      if (ann is A)
        yield(target to ann)
    }

    target.superclasses.forEach { queue.add(it) }
  }
}
