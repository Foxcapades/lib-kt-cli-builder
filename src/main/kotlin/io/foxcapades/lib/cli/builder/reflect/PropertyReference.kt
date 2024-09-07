package io.foxcapades.lib.cli.builder.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class PropertyReference<T : Any, V>(
  override val type:     KClass<out T>,
  override val accessor: KProperty1<T, V>,
) : ValueAccessorReference<T, V, KProperty1<T, V>> {
  override fun getValue(instance: T) = accessor.get(instance)

  override fun toString() = "Property:${type.safeName}::${accessor.name}"
}

