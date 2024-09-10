package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal class PropertyReference<T : Any, V>(
  override val containingType:     KClass<out T>,
  override val accessor: KProperty1<T, V>,
) : ValueAccessorReference<T, V, KProperty1<T, V>> {
  override val qualifiedName: String
    get() = "property " + super.qualifiedName

  override fun getValue(instance: T) = accessor.get(instance)

  override fun toString() = "Property:${containingType.safeName}::${accessor.name}"
}

