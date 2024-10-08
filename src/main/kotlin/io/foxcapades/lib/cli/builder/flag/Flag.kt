package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.kt.prop.delegation.MutableDefaultableProperty
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<V> : CliCallComponent, MutableDefaultableProperty<V> {
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
  val argument: Argument<V>

  /**
   * Indicates whether this flag is required to be present in CLI calls.
   */
  val isRequired: Boolean

  /**
   * Method used to indicate whether a [Flag] instance should be included in
   * serialization based on customizable logic.
   *
   * `Flag` instances that are marked with [isRequired] = `true` will be always
   * be included in serialization.  For such instances, this method will not be
   * called.
   *
   * Implementers should indicate if/when they do not make use of a call to
   * [Argument.shouldSerialize].
   *
   * @param config Current serialization configuration.
   *
   * @param source Information about the source of the `Flag` instance.  A
   * `Flag` source will typically be a class property.
   *
   * @return `true` if this `Flag` instance should be included in serialization
   * output, otherwise `false` if this `Flag` should be omitted.
   */
  fun shouldSerialize(config: CliSerializationConfig, source: ValueSource): Boolean

  fun writeToString(writer: CliFlagWriter<*, V>)

  override val hasDefault
    get() = argument.hasDefault

  override val isSet: Boolean
    get() = argument.isSet

  override fun get() = argument.get()

  override fun set(value: V) = argument.set(value)

  override fun unset() = argument.unset()

  override fun getDefault() = argument.getDefault()
}

