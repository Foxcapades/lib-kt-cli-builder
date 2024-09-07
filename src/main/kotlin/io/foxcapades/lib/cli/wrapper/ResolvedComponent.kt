package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

/**
 * Represents a component that has been resolved to a single instance and
 * class property.
 *
 * @param T Component container class type.
 *
 * @param V Component value type.
 *
 * @since 1.0.0
 */
sealed interface ResolvedComponent<T : Any, V> : CliCallComponent, ValueAccessorReference<T, V, KCallable<V>> {
  val instance: T

  fun getValue() = getValue(instance)
}
