package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass

interface ValueAccessorReference<T : Any, V, out A : KCallable<V>> : TypeReference<T> {
  val accessor: A

  val name
    get() = accessor.name

  val isNullable: Boolean
    get() = accessor.returnType.isMarkedNullable

  val valueClass: KClass<*>?
    get() = accessor.returnType.classifier as? KClass<*>

  override val qualifiedName
    get() = super.qualifiedName + "::" + accessor.name

  fun getValue(instance: T): V = accessor.call(instance)
}
