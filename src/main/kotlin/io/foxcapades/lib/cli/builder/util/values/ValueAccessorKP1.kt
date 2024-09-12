package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KProperty1

internal class ValueAccessorKP1<T : Any, V>(member: KProperty1<T, V>, instance: T) : ValueAccessor<V> {
  private val member = member

  override val instance = instance

  override val name
    get() = member.name

  override val hasName: Boolean
    get() = true

  override val reference
    get() = member.qualifiedName(instance::class)

  override val kind
    get() = ValueSource.Kind.Getter

  override val containerType
    get() = instance::class

  override fun invoke() =
    member(instance)
}
