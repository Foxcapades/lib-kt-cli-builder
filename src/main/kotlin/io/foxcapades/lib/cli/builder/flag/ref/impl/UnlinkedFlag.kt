package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.impl.UnlinkedArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.UnlinkedResolvedFlag
import io.foxcapades.lib.cli.builder.flag.safeName
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.safeName
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal class UnlinkedFlag<V>(
  parent: ResolvedCommand<*>,
  instance: Flag<Argument<V>, V>
) : UnlinkedResolvedFlag<V> {
  private val instance = instance

  override val parentComponent = parent

  override val qualifiedName by lazy { "flag " + parentComponent.type.safeName + "::??? " + instance.safeName }

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

  override val argument by lazy { UnlinkedArgument(this, instance.argument) }

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

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = instance.shouldSerialize(config, reference)

  override fun writeToString(writer: CliFlagWriter<*, V>) =
    instance.writeToString(writer)
}
