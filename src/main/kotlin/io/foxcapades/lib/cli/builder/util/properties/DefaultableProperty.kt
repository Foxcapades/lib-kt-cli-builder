package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.lib.cli.builder.util.NoSuchDefaultValueException
import io.foxcapades.lib.cli.builder.util.NoSuchValueException
import kotlin.reflect.KProperty

/**
 * Represents a generic value which may or may not be set along with a fallback
 * default value which also may or may not be set.
 *
 * @param T Type of value this `DefaultableProperty` may contain.
 *
 * @since 1.0.0
 */
interface DefaultableProperty<T> : Property<T> {
  /**
   * Indicates whether this `DefaultableProperty` has a default value set.
   *
   * If `hasDefault` is `false`, calls to [getDefault] will result in an
   * exception being thrown.
   */
  val hasDefault: Boolean

  /**
   * Returns the default fallback value contained in this [DefaultableProperty]
   * instance, if one is set.
   *
   * If no default value is currently set on this `DefaultableProperty`, this
   * method will throw an exception.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchDefaultValueException If this method is called when the
   * property has no default set.
   */
  @Throws(NoSuchDefaultValueException::class)
  fun getDefault(): T

  /**
   * Returns either the current value of this `DefaultableProperty`, if one is
   * set, or the default value, if one is set.
   *
   * If neither a value nor a default value have been set on this
   * `DefaultableProperty`, this method will throw an exception.
   *
   * @return Either the value of this property, or its default fallback value.
   *
   * @throws NoSuchValueException If neither a value nor default value are set
   * on this `DefaultableProperty`.
   */
  @Throws(NoSuchValueException::class)
  fun getOrDefault(): T = if (!isSet && hasDefault) getDefault() else get()

  /**
   * Overrides the parent implementation [Property.getValue] with a default that
   * calls [getOrDefault] instead of [get].
   *
   * Specific implementations may alter the behavior of this method, but are
   * expected to adhere to the rules defined by [getOrDefault].
   *
   * If neither a value nor a default value have been set on this
   * `DefaultableProperty`, this method will throw an exception.
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @return Either the value of this property, or its default fallback value.
   *
   * @throws NoSuchValueException If neither a value nor default value are set
   * on this `DefaultableProperty`.
   */
  @Throws(NoSuchValueException::class)
  override operator fun getValue(thisRef: Any?, property: KProperty<*>) = getOrDefault()
}
