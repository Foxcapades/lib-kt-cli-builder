package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal abstract class AbstractArgument<V>(
  parent:   ResolvedComponent,
  instance: Argument<V>,
  source:   ValueSource,
)
  : ResolvedArgument<V>
{
  private val instance = instance

  override val parentComponent = parent

  override val valueSource = source

  override val isRequired
    get() = instance.isRequired

  override val shouldQuote
    get() = instance.shouldQuote

  override val hasDefault
    get() = instance.hasDefault

  override val isSet
    get() = instance.isSet

  override fun get() =
    instance.get()

  override fun getDefault() =
    instance.getDefault()

  override fun set(value: V) =
    instance.set(value)

  override fun unset() =
    instance.unset()

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    instance.shouldSerialize(config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    instance.writeToString(writer)
}
