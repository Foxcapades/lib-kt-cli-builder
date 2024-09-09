package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal class UnlinkedArgument<V>(
  parent:   ResolvedComponent,
  instance: Argument<V>,
) : UnlinkedResolvedArgument<V> {
  private val instance = instance

  override val parentComponent = parent

  override val qualifiedName
    get() = if (parentComponent is ResolvedFlag<*>) {
      "argument of " + parentComponent.qualifiedName
    } else {
      parentComponent.qualifiedName + " argument"
    }

  override val shouldQuote
    get() = instance.shouldQuote

  override val isRequired
    get() = instance.isRequired

  override val isSet
    get() = instance.isSet

  override val hasDefault
    get() = instance.hasDefault

  override val isDefault
    get() = instance.isDefault

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

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    instance.writeToString(writer)
}
