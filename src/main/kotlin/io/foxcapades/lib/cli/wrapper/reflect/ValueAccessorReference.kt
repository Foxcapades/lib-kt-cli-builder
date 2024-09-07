package io.foxcapades.lib.cli.wrapper.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ValueAccessorReference<T : Any, V, A : KCallable<V>> : TypeReference<T> {
  val accessor: A

  val name
    get() = accessor.name

  val isNullable: Boolean
    get() = accessor.returnType.isMarkedNullable

  val valueClass: KClass<*>?
    get() = accessor.returnType.classifier as? KClass<*>

  fun getValue(instance: T): V = accessor.call(instance)
}
