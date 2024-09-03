package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.MutableProperty

/**
 * Represents a single positional or flag argument.
 */
interface Argument<T> : MutableProperty<T>, CliCallComponent {
  @get:Throws(UnsetArgumentDefaultException::class)
  val default: T

  val hasDefault: Boolean

  val isRequired get() = !hasDefault

  val shouldQuote: Boolean

  fun getOrNull() = if (isSet) get() else if (hasDefault) default else null

  fun getOrDefault(value: T) = getOrNull() ?: value

  /**
   * Tests whether this `Argument` is currently set to its [default] value.
   *
   * If [hasDefault] is `false`, this method will also return `false`.
   *
   * If [isSet] is `false`, this method will always return the same value as
   * [hasDefault].
   *
   * If [isSet] is `true`, this method will compare the set value to the default
   * to determine the return value.
   *
   * @param config Serialization config.  Some implementations may require
   * converting this [Argument]'s value to a string in order to test whether the
   * value is the configured default.  In those cases, serialization config info
   * may be required to correctly serialize the value.
   *
   * @return `true` if this `Argument` has a default set, and the current value
   * of this `Argument` is equal to that default.
   */
  fun isDefault(config: CliSerializationConfig): Boolean

  fun writeToString(builder: CliArgumentAppender)
}

