package io.foxcapades.lib.cli.builder.component

import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
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
interface ResolvedComponentOld<T : Any, V> : CliCallComponent, ValueAccessorReference<T, V, KCallable<V>> {
  val instance: T

  fun getValue() = getValue(instance)
}

