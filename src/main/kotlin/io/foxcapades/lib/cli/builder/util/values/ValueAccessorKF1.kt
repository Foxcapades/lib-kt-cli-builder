package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KFunction1

/**
 * Represents a getter reference.
 *
 * @param V Type of the value returned by the given [getter][member].
 *
 * @param member Getter reference.
 *
 * @param instance An instance of the class that [member] belongs to which will
 * be passed to `member` as the `this` parameter when invoked.
 */
internal class ValueAccessorKF1<T : Any, V>(member: KFunction1<T, V>, instance: T) : ValueAccessor<V> {
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
