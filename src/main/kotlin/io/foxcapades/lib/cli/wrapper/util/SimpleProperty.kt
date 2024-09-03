package io.foxcapades.lib.cli.wrapper.util

internal class SimpleProperty<T> : MutableProperty<T> {
  private var value: T? = null

  constructor()

  constructor(value: T) {
    set(value)
  }

  override var isSet = false
    private set

  @Suppress("UNCHECKED_CAST")
  override fun get() = if (!isSet) throw NoSuchValueException() else value as T

  override fun set(value: T) {
    this.value = value
    this.isSet = true
  }

  override fun unset() {
    value = null
    isSet = false
  }

  @Suppress("UNCHECKED_CAST")
  inline fun <R> map(fn: (T) -> R): SimpleProperty<R> =
    getOrNull()
      ?.let(fn)
      ?.let(::SimpleProperty)
      ?.apply { isSet = true } as SimpleProperty<R>?
      ?: SimpleProperty()
}
