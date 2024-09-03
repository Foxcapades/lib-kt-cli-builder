package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import io.foxcapades.lib.cli.wrapper.util.getOrNull

internal sealed class AbstractArgument<T>(
  override val hasDefault: Boolean,
  override val shouldQuote: Boolean,
) : Argument<T> {
  protected val value = SimpleProperty<T>()

  override val isSet by value::isSet

  override fun isDefault(config: CliSerializationConfig) =
    hasDefault && (!isSet || value.get() == default)

  override fun get() = if (hasDefault) value.getOrNull() ?: default else value.get()

  override fun set(value: T) = this.value.set(value)

  override fun unset() = value.unset()
}