package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.kt.prop.delegation.MutableDefaultableDelegateProperty
import kotlin.reflect.KProperty

internal abstract class AbstractDelegate<T>(
  state:   Byte,
  default: T?,
  value:   T?
)
  : MutableDefaultableDelegateProperty<T, T?>
  , AbstractProperty<T>(state, default, value)
{
  override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
    when {
      isSet      -> get()
      hasDefault -> getDefault()
      else       -> null
    }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) =
    if (value == null)
      unset()
    else
      set(value)
}
