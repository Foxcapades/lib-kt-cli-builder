package io.foxcapades.lib.cli.builder.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1

class GetterReference<T : Any, V>(
  override val type:     KClass<out T>,
  override val accessor: KFunction1<T, V>,
) : ValueAccessorReference<T, V, KFunction1<T, V>> {
  override fun getValue(instance: T) = accessor.invoke(instance)

  override fun toString() = "Method:${type.safeName}::${accessor.name}"
}
