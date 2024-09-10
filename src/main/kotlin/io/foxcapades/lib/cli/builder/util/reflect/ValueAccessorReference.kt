package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ValueAccessorReference<T : Any, V, out A : KCallable<V>> {
  val containingType: KClass<out T>

  val accessor: A

  val name
    get() = accessor.name

  val isNullable: Boolean
    get() = accessor.returnType.isMarkedNullable

  val valueClass: KClass<*>?
    get() = accessor.returnType.classifier as? KClass<*>

  val qualifiedName
    get() = containingType.safeName + "::" + accessor.name

  fun getValue(instance: T): V = accessor.call(instance)
}
