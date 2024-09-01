package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliCallComponent
import kotlin.reflect.KProperty

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<A: Argument<V>, V> : CliCallComponent {
  /**
   * Indicates whether this flag has a long form.
   */
  val hasLongForm: Boolean

  /**
   * Long form of this flag.
   */
  val longForm: String

  /**
   * Indicates whether this flag has a short form.
   */
  val hasShortForm: Boolean

  /**
   * Short form of this flag.
   */
  val shortForm: Byte

  /**
   * Argument for this flag.
   */
  val argument: A

  /**
   * Indicates whether this flag is required to be present in CLI calls.
   */
  val isRequired: Boolean

  /**
   * Indicates whether this flag has been set to a value.
   */
  val isSet get() = argument.isSet

  /**
   * Indicates whether this flag is currently equivalent to its default value.
   *
   * If [isSet] returns `true` then the value that has been set for the
   * [Argument] attached to this [Flag] is equal to the default provided when
   * the `Flag` instance was created.
   *
   * If [isSet] returns `false`, then a default value was provided, and no value
   * has been explicitly set.
   *
   * If [hasDefault] is `false`, this property will also be `false`.
   */
  val isDefault get() = argument.isDefault

  /**
   * Indicates whether a default value has been set for this [Flag] instance's
   * underlying [Argument].
   *
   * If this value is `false`, attempting to access the `Flag`'s [default]
   * property will cause an [UnsetArgumentDefaultException] to be thrown.
   *
   * @see [Argument.hasDefault]
   * @see [default]
   */
  val hasDefault get() = argument.hasDefault

  /**
   * The default value set on this [Flag] instance's underlying [Argument].
   *
   * If no default was set, accessing this property will cause an
   * [UnsetArgumentDefaultException] to be thrown.
   *
   * @see [hasDefault]
   * @see [Argument.default]
   */
  @get:Throws(UnsetArgumentDefaultException::class)
  val default get() = argument.default

  fun get(): V

  operator fun getValue(ref: Any?, property: KProperty<*>): V

  fun set(value: V)

  operator fun setValue(ref: Any?, property: KProperty<*>, value: V)

  fun unset()
}