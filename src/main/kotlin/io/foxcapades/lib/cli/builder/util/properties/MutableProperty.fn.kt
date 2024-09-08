@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

fun <T> MutableProperty(): MutableProperty<T> = SimpleMutableProperty()

fun <T> MutableProperty(value: T): MutableProperty<T> = SimpleMutableProperty(value)

private class SimpleMutableProperty<T> : MutableProperty<T> {
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

  override fun set(value: T) {
    this.value = value
    this.isSet = true
  }

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
