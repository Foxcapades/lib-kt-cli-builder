package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal abstract class AbstractValueFlag<T : Any, V>(
  parent:   ResolvedCommand<T>,
  instance: Flag<V>,
  source: ValueSource,
)
  : ResolvedFlag<V>
{
  private val instance = instance

  override val parentComponent = parent

  override val valueSource = source

  override val hasLongForm
    get() = instance.hasLongForm

  override val longForm
    get() = instance.longForm

  override val hasShortForm
    get() = instance.hasShortForm

  override val shortForm
    get() = instance.shortForm

  override val isRequired
    get() = instance.isRequired

  override val isSet
    get() = instance.isSet

  override val hasDefault
    get() = instance.hasDefault

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

  override fun writeToString(writer: CliFlagWriter<*, V>) =
    instance.writeToString(writer)
}
