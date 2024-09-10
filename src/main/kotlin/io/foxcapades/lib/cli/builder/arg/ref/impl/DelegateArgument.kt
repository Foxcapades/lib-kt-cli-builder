package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedDelegatedArgumentRef
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal open class DelegateArgument<T : Any, V>(
  parent:   ResolvedCommand<T>,
  delegate: Argument<V>,
  accessor: KCallable<V>,
)
  : ResolvedDelegatedArgumentRef<T, V>
{
  private val delegate = delegate

  override val parentComponent = parent

  override val accessor = accessor

  override val isRequired
    get() = delegate.isRequired

  override val shouldQuote
    get() = delegate.shouldQuote

  override val hasDefault
    get() = delegate.hasDefault

  override val isSet
    get() = delegate.isSet

  override val isDefault
    get() = delegate.isDefault

  override fun get() = delegate.get()

  override fun getValue(thisRef: Any?, property: KProperty<*>) = delegate.getValue(thisRef, property)

  override fun getDefault() = delegate.getDefault()

  override fun getOrDefault() = delegate.getOrDefault()

  override fun set(value: V) = delegate.set(value)

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) = delegate.setValue(thisRef, property, value)

  override fun unset() = delegate.unset()

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = delegate.shouldSerialize(config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) = delegate.writeToString(writer)
}
