package io.foxcapades.lib.cli.wrapper.util

import kotlin.reflect.KProperty

interface Property<T> {
  val isSet: Boolean

  fun get(): T

  operator fun getValue(thisRef: Any?, property: KProperty<*>) = get()
}
