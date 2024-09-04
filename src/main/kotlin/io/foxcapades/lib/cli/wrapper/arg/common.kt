package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.UnsetArgumentDefaultException
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import io.foxcapades.lib.cli.wrapper.util.getOrNull
import io.foxcapades.lib.cli.wrapper.util.getOrThrow

internal sealed class AbstractArgument<T>(
  default: Property<T>,
  override val isRequired: Boolean,
  override val shouldQuote: Boolean,
) : Argument<T> {
  private val hiddenDefault: Property<T> = default

  protected val value = SimpleProperty<T>()

  override val default
    get() = hiddenDefault.getOrThrow(::UnsetArgumentDefaultException)

  override val hasDefault
    get() = hiddenDefault.isSet

  override val isSet by value::isSet

  override fun isDefault(config: CliSerializationConfig) =
    hasDefault && (!isSet || value.get() == default)

  override fun get() =
    if (hasDefault)
      value.getOrNull() ?: default
    else
      value.get()

  override fun set(value: T) = this.value.set(value)

  override fun unset() = value.unset()
}
