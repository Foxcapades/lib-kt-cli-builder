package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.MutableProperty

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<A: Argument<V>, V> : MutableProperty<V>, CliCallComponent {
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
  val shortForm: Char

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
  override val isSet get() = argument.isSet

  /**
   * Indicates whether a default value has been set for this [Flag] instance's
   * underlying [Argument].
   *
   * If this value is `false`, attempting to access the `Flag`'s [default]
   * property will cause an [UnsetArgumentDefaultException] to be thrown.
   *
   * @see [Argument.hasDefault]
   */
  val hasDefault get() = argument.hasDefault

  /**
   * Tests whether this `Flag`'s underlying [Argument] is currently set to its
   * default value.
   *
   * See [Argument.isDefault] for additional information.
   *
   * @param config Serialization config.  Some implementations may require
   * converting this `Flag`'s underlying `Argument` value to a string in order
   * to test whether the value is the configured default.  In those cases,
   * serialization config info may be required to correctly serialize the value.
   *
   * @return `true` if this `Flag` has a default set, and the current value
   * of this `Flag` is equal to that default.
   *
   * @see Argument.isDefault
   */
  fun isDefault(config: CliSerializationConfig): Boolean =
    argument.isDefault(config)

  override fun get() = argument.get()

  override fun set(value: V) = argument.set(value)

  override fun unset() = argument.unset()

  fun writeToString(builder: CliAppender)
}
