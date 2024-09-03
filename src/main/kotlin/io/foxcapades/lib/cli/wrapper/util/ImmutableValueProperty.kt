package io.foxcapades.lib.cli.wrapper.util

@JvmInline
value class ImmutableValueProperty<T>(private val value: T) : Property<T> {
  override val isSet
    get() = true

  override fun get() = value
}