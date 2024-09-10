package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.MutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<out A : Argument<V>, V> : MutableDefaultableProperty<V>, CliCallComponent {
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
   * Method used to indicate whether a [Flag] instance should be included in
   * serialization based on customizable logic.
   *
   * Different implementations may provide varying default serialization
   * inclusion rules, however the default behavior provided by this interface is
   * to always include flags whose arguments return `true` on calls to
   * [Argument.shouldSerialize].
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
   * @param reference Reference to the class property or method from which the
   * `Flag` instance was obtained.  If the `Flag` instance is not a property
   * delegate, this parameter will be `null`.
   *
   * @return `true` if this `Flag` instance should be included in serialization
   * output, otherwise `false` if this `Flag` should be omitted.
   */
  fun shouldSerialize(config: CliSerializationConfig, reference: ValueAccessorReference<*, V, KCallable<V>>?): Boolean =
    argument.shouldSerialize(config, reference)

  fun writeToString(writer: CliFlagWriter<*, V>)

  override val isSet: Boolean
    get() = argument.isSet

  override val hasDefault: Boolean
    get() = argument.hasDefault

  override fun get() = argument.get()

  override fun set(value: V) = argument.set(value)

  override fun unset() = argument.unset()

  override fun getDefault() = argument.getDefault()
}
