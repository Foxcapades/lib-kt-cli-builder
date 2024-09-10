package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.FlagValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedDelegateFlagRef
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal open class DelegateFlag<T : Any, V>(
  parent:   ResolvedCommand<T>,
  delegate: Flag<Argument<V>, V>,
  accessor: KCallable<V>,
) : ResolvedDelegateFlagRef<T, V> {
  private val delegate = delegate

  override val parentComponent = parent

  override val accessor = accessor

  override val hasLongForm
    get() = delegate.hasLongForm

  override val longForm
    get() = delegate.longForm

  override val hasShortForm
    get() = delegate.hasShortForm

  override val shortForm
    get() = delegate.shortForm

  override val isRequired
    get() = delegate.isRequired

  override val isSet: Boolean
    get() = delegate.isSet

  override val hasDefault: Boolean
    get() = delegate.hasDefault

  override val argument: ResolvedArgument<V> by lazy { FlagValueArgument(this, delegate.argument) }

  override fun get() = delegate.get()

  override fun getValue(thisRef: Any?, property: KProperty<*>) = delegate.getValue(thisRef, property)

  override fun getDefault() = delegate.getDefault()

  override fun getOrDefault() = delegate.getOrDefault()

  override fun set(value: V) = delegate.set(value)

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) = delegate.setValue(thisRef, property, value)

  override fun unset() = delegate.unset()

  override fun shouldSerialize(
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = delegate.shouldSerialize(config, reference)

  override fun writeToString(writer: CliFlagWriter<*, V>) = delegate.writeToString(writer)
}
