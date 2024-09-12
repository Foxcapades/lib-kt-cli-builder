package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.UnsetArgumentDefaultException
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.MutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.values.ValueSource

/**
 * Represents a single positional or flag argument.
 *
 * @since 1.0.0
 */
interface Argument<V> : MutableDefaultableProperty<V>, CliCallComponent {
  val isRequired: Boolean

  val shouldQuote: Boolean

  val isDefault: Boolean
    get() = hasDefault && isSet && getDefault() == get()

  fun shouldSerialize(config: CliSerializationConfig, source: ValueSource): Boolean

  fun writeToString(writer: CliArgumentWriter<*, V>)

  @Throws(UnsetArgumentDefaultException::class)
  override fun getDefault(): V
}
