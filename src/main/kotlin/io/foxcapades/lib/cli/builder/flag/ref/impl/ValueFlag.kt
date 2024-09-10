package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.arg.ref.impl.FlagValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlagValueRef
import io.foxcapades.lib.cli.builder.serial.CliFlagWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

internal open class ValueFlag<T : Any, V>(
  parent: ResolvedCommand<T>,
  instance: Flag<Argument<V>, V>,
  accessor: KCallable<Flag<Argument<V>, V>>,
) : ResolvedFlagValueRef<T, V> {
  private val instance = instance

  override val parentComponent = parent

  override val accessor = accessor

  override val containingType
    get() = parentComponent.type

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

  override val argument: ResolvedArgument<V> by lazy { FlagValueArgument(this, instance.argument) }

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
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = instance.shouldSerialize(config, reference)

  override fun writeToString(writer: CliFlagWriter<*, V>) =
    instance.writeToString(writer)
}
