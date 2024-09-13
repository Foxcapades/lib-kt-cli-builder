package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.kt.prop.delegation.MutableDefaultableDelegateProperty
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import kotlin.reflect.KProperty

interface DelegateFlag<V> : Flag<V>, MutableDefaultableDelegateProperty<V, V?> {
  override val argument: DelegateArgument<V>

  override val isSet
    get() = argument.isSet

  override val hasDefault
    get() = argument.hasDefault

  override fun getValue(thisRef: Any?, property: KProperty<*>): V? = argument.getValue(thisRef, property)

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: V?) = argument.setValue(thisRef, property, value)
}
