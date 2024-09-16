package io.foxcapades.lib.cli.builder.util.values

import kotlin.reflect.KCallable

/**
 * Represents a [ValueSource] that is a getter or property from which a value
 * may be retrieved on demand.
 *
 * @param V Type of value returned by the underlying getter or property.
 *
 * @since 1.0.0
 */
interface ValueAccessor<V> : ValueSource {
  /**
   * Instance of the accessor itself.
   *
   * **WARNING**: The return type of this accessor is not required to be in any
   * way related to [V].  The accessor may contain a type that wraps or produces
   * values of type `V`, in which case the accessor value itself may or may not
   * be compatible with type `V`.
   */
  val accessorInstance: KCallable<*>

  /**
   * Invoke the underlying source accessor to get the current value of the
   * [ValueSource].
   *
   * @return The current value of the `ValueSource`.
   */
  operator fun invoke(): V
}
