package io.foxcapades.lib.cli.builder.util.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1

internal class GetterReference<T : Any, V>(
  override val containingType:     KClass<out T>,
  override val accessor: KFunction1<T, V>,
) : ValueAccessorReference<T, V, KFunction1<T, V>> {
  override val qualifiedName: String
    get() = "getter " + super.qualifiedName

  override fun getValue(instance: T) = accessor.invoke(instance)

  override fun toString() = "Method:${containingType.safeName}::${accessor.name}"
}
