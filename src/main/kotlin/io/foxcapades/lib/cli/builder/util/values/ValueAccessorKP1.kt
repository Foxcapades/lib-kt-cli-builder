package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty1

/**
 * Represents a property reference.
 *
 * @param V Type of the value returned by the given [property][member].
 *
 * @param member Property reference.
 *
 * @param instance An instance of the class that [member] belongs to which will
 * be passed to `member` as the `this` parameter when invoked.
 */
internal class ValueAccessorKP1<T : Any, V>(member: KProperty1<T, V>, instance: T) : ValueAccessor<V> {
  private val member = member

  override val containerInstance = instance

  override val accessorInstance
    get() = member

  override val name
    get() = member.name

  override val hasName: Boolean
    get() = true

  override val reference
    get() = member.qualifiedName(containerInstance::class)

  override val kind
    get() = ValueSource.Kind.Getter

  override val containerType
    get() = containerInstance::class

  override fun invoke() =
    member(containerInstance)
}
