package io.foxcapades.lib.cli.builder.util.values

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
   * Invoke the underlying source accessor to get the current value of the
   * [ValueSource].
   *
   * @return The current value of the `ValueSource`.
   */
  operator fun invoke(): V
}
