package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

internal class WrapperAccessorK0<V>(
  fn:     () -> V,
  member: KCallable<*>,
  parent: KClass<*>?
) : ValueAccessor<V> {
  private val fn = fn

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

  override fun invoke() = fn()
}
