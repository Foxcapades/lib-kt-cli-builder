package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty0

internal class ValueAccessorKP0<V>(member: KProperty0<V>, parent: KClass<*>?) : ValueAccessor<V> {
  private val member = member

  override val containerType = parent

  override val name
    get() = member.name

  override val hasName: Boolean
    get() = true

  override val reference
    get() = member.qualifiedName(containerType)

  override val kind
    get() = ValueSource.Kind.Property

  override val instance: Any?
    get() = null

  override fun invoke() =
    member()
}
