package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.kt.prop.delegation.MutableDefaultableProperty
import io.foxcapades.lib.cli.builder.component.CliCallComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

/**
 * Represents a single positional or flag argument.
 *
 * @since 1.0.0
 */
interface Argument<V> : CliCallComponent, MutableDefaultableProperty<V> {
  val isRequired: Boolean

  val shouldQuote: Boolean

  fun shouldSerialize(config: CliSerializationConfig, source: ValueSource): Boolean

  fun writeToString(writer: CliArgumentWriter<*, V>)
}

