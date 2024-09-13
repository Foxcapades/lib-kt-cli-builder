@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.kt.prop.delegation.MutableDelegateProperty
import io.foxcapades.kt.prop.delegation.MutableProperty
import io.foxcapades.kt.prop.delegation.NoSuchValueException
import kotlin.reflect.KProperty

internal fun <T> MutableDelegateProperty(): MutableDelegateProperty<T, T> = SimpleMutableProperty()

private class SimpleMutableProperty<T> : MutableDelegateProperty<T, T> {
  override var isSet: Boolean
    private set

  private var value: T?

  constructor() {
    this.value = null
    this.isSet = false
  }

  constructor(value: T) {
    this.value = value
    this.isSet = true
  }

  @Suppress("UNCHECKED_CAST")
  override fun get() =
    if (isSet)
      value as T
    else
      throw NoSuchValueException()

  override fun getValue(thisRef: Any?, property: KProperty<*>) = get()

  override fun set(value: T) {
    this.value = value
    this.isSet = true
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = set(value)

  override fun unset() {
    this.value = null
    this.isSet = false
  }
}

inline fun <T> MutableProperty<T>.setIfAbsent(value: T) =
  also { if (!isSet) set(value) }


inline fun <T> MutableProperty<T>.computeIfAbsent(fn: () -> T): MutableProperty<T> {
  if (!isSet)
    set(fn())

  return this
}
