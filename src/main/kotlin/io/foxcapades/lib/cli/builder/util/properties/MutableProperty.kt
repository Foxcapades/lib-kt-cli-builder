package io.foxcapades.lib.cli.builder.util.properties

import kotlin.reflect.KProperty

/**
 * Represents a replaceable/settable generic value which may or may not exist.
 *
 * @param T Type of value this `MutableProperty` may contain.
 *
 * @since 1.0.0
 */
interface MutableProperty<T> : Property<T> {
  /**
   * Sets the value wrapped by this `MutableProperty` to the value provided.
   *
   * If this `MutableProperty` was already set, the provided value will replace
   * the previous one.
   *
   * @param value Value to set for this `MutableProperty`.
   */
  fun set(value: T)

  /**
   * Enables the use of [MutableProperty] instances as mutable class property
   * delegates (`var`).
   *
   * If this `MutableProperty` was already set, the provided value will replace
   * the previous one.
   *
   * Specific implementations may alter the behavior of this method, however the
   * default implementation simply calls [set].
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @param value Value to set for this `MutableProperty`.
   */
  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = set(value)

  /**
   * Removes any value previously set on this `MutableProperty` instance.
   *
   * If this `MutableProperty` instance was already unset, this method does
   * nothing.
   *
   * After this method returns, [isSet] will return `false`, and calls to [get]
   * will throw an exception until a new value has been [set].
   */
  fun unset()
}
