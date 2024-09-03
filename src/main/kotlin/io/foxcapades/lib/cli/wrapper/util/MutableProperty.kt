package io.foxcapades.lib.cli.wrapper.util

import kotlin.reflect.KProperty

interface MutableProperty<T> : Property<T> {
  fun set(value: T)

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = set(value)

  fun unset()
}