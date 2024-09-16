package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KClass
import kotlin.reflect.KFunction0

/**
 * Represents a curried getter reference which already has a value for its
 * `this` parameter.
 *
 * @param V Type of the value returned by the given [getter][member].
 *
 * @param member Getter instance reference.
 *
 * @param parent Type the getter came from, if known.  This value may not be
 * known if the getter was returned by [Command.getCliCallComponents].
 */
internal class ValueAccessorKF0<V>(member: KFunction0<V>, parent: KClass<*>?) : ValueAccessor<V> {
  private val member = member

  override val containerType = parent

  override val accessorInstance
    get() = member

  override val name
    get() = member.name

  override val hasName: Boolean
    get() = true

  override val reference
    get() = member.qualifiedName(containerType)

  override val kind
    get() = ValueSource.Kind.Getter

  override val containerInstance: Any?
    get() = null

  override fun invoke() =
    member()
}
