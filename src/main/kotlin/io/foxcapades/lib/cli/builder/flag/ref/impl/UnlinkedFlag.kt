package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.UnlinkedArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.UnlinkedResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import kotlin.reflect.KProperty

internal open class UnlinkedFlag<V>(
  parent:   ResolvedCommand<*>,
  instance: Flag<V>,
  source:   ValueSource,
)
  : UnlinkedResolvedFlag<V>
{
  private val instance = instance

  override val valueSource = source

  override val parentComponent = parent

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

  override val argument: ResolvedArgument<V> by lazy { UnlinkedArgument(this, instance.argument, valueSource) }

  override fun get() =
    instance.get()

  override fun getValue(thisRef: Any?, property: KProperty<*>) =
    instance.getValue(thisRef, property)

  override fun getDefault() =
    instance.getDefault()

  override fun getOrDefault() =
    instance.getOrDefault()

  override fun set(value: V) =
    instance.set(value)

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) =
    instance.setValue(thisRef, property, value)

  override fun unset() =
    instance.unset()

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    instance.shouldSerialize(config, source)

  override fun writeToString(writer: CliFlagWriter<*, V>) =
    instance.writeToString(writer)
}
