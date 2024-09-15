package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KCallable

internal class /**
 * Represents a value accessor wrapping a getter or class property.
 *
 * @param V Type of the value returned by the given [function][fn].
 *
 * @param fn Function which may be invoked to get the underlying value for the
 * target class member.
 *
 * @param member Reference to the actual class member wrapped by [fn].
 *
 * @param instance An instance of the class that [member] belongs to.
 */
WrapperAccessorK1<T : Any, V>(
  fn:       () -> V,
  member:   KCallable<*>,
  instance: T,
) : ValueAccessor<V> {
  private val fn = fn

  private val member = member

  override val instance = instance

  override val containerType
    get() = instance::class

  override val name
    get() = member.name

  override val hasName: Boolean
    get() = true

  override val reference
    get() = member.qualifiedName(containerType)

  override val kind
    get() = ValueSource.Kind.Property

  override fun invoke() = fn()
}
