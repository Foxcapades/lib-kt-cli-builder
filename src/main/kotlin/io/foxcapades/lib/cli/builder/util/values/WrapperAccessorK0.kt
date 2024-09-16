package io.foxcapades.lib.cli.builder.util.values

import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.util.reflect.qualifiedName
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

/**
 * Represents a curried value accessor wrapping a getter or instance property.
 *
 * @param V Type of the value returned by the given [curried function][fn].
 *
 * @param fn Function which may be invoked to get the underlying value for the
 * target class member.
 *
 * @param member Reference to the actual class member wrapped by [fn].
 *
 * @param parent Type the member came from, if known.  This value may not be
 * known if the member was returned by [Command.getCliCallComponents].
 */
internal class WrapperAccessorK0<V>(
  fn:     () -> V,
  member: KCallable<*>,
  parent: KClass<*>?
) : ValueAccessor<V> {
  private val fn = fn

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
    get() = ValueSource.Kind.Property

  override val containerInstance: Any?
    get() = null

  override fun invoke() = fn()
}
