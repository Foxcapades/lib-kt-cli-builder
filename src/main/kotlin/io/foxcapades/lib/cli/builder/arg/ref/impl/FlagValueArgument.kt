package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal class FlagValueArgument<V>(parent: ResolvedFlag<V>, instance: Argument<V>) : ResolvedArgument<V> {
  private val actual = instance

  override val parentComponent = parent

  override val isRequired
    get() = actual.isRequired

  override val shouldQuote
    get() = actual.shouldQuote

  override val hasDefault
    get() = actual.hasDefault

  override val isSet
    get() = actual.isSet

  override val isDefault
    get() = actual.isDefault

  override val qualifiedName: String
    get() = "argument of " + parentComponent.qualifiedName

  override fun get() = actual.get()

  override fun getValue(thisRef: Any?, property: KProperty<*>) = actual.getValue(thisRef, property)

  override fun getDefault() = actual.getDefault()

  override fun getOrDefault() = actual.getOrDefault()

  override fun set(value: V) = actual.set(value)

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) = actual.setValue(thisRef, property, value)

  override fun unset() = actual.unset()

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = actual.shouldSerialize(config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>)  = actual.writeToString(writer)
}
