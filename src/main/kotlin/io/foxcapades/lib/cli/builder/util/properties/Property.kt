package io.foxcapades.lib.cli.builder.util.properties

import kotlin.reflect.KProperty

/**
 * Represents a generic value which may or may not exist.
 *
 * Similar to Java's [java.util.Optional] type, but allowing for class property
 * delegation.
 *
 * @param T Type of value this `Property` may contain.
 *
 * @since 1.0.0
 */
interface Property<T> {
  /**
   * Indicates whether this [Property] currently contains a value.
   *
   * If `isSet` is `false`, then calling [get] will result in an exception being
   * thrown.
   */
  val isSet: Boolean

  /**
   * Returns the value contained in this [Property] instance, if one is set.
   *
   * If no value is currently set on this `Property`, this method will throw an
   * exception.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchValueException If this method is called when the property has
   * no value set.
   */
  @Throws(NoSuchValueException::class)
  fun get(): T

  /**
   * Enables the use of [Property] instances as read-only class property
   * delegates (`val`).
   *
   * Specific implementations may alter the behavior of this method, however the
   * default implementation simply calls [get].
   *
   * If no value is currently set on this `Property`, this method will throw an
   * exception.
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchValueException If this method is called when the property has
   * no value set.
   */
  @Throws(NoSuchValueException::class)
  operator fun getValue(thisRef: Any?, property: KProperty<*>) = get()
}
