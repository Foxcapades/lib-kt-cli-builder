package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.UnsetArgumentDefaultException
import io.foxcapades.lib.cli.wrapper.reflect.shouldQuote
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.NonNullGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*
import kotlin.reflect.KClass

internal class GeneralArgumentImpl<T>(
  default: Property<T>,
  override val shouldQuote: Boolean,
  override val isRequired: Boolean,
  private val formatter: ArgumentFormatter<T>
) : Argument<T> {
  constructor(
    type: KClass<*>,
    nullable: Boolean,
    default: Property<T>,
    shouldQuote: Property<Boolean>,
    isRequired: Property<Boolean>,
    formatter: Property<ArgumentFormatter<T>>
  ) : this(
    default     = default,
    shouldQuote = shouldQuote.getOrCompute { type.shouldQuote() },
    isRequired  = isRequired.getOr(false),
    formatter   = formatter.getOrCompute {
      @Suppress("UNCHECKED_CAST")
      (if (nullable) NullableGeneralStringifier else NonNullGeneralStringifier) as ArgumentFormatter<T>
    }
  )
  private val nd = default

  private val value = SimpleProperty<T>()

  override val isSet by value::isSet

  override val hasDefault by nd::isSet

  override val default
    get() = nd.getOrThrow(::UnsetArgumentDefaultException)

  override fun get() = value.get()

  override fun set(value: T) = this.value.set(value)

  override fun unset() = value.unset()

  override fun isDefault(config: CliSerializationConfig) =
    hasDefault && (!isSet || default == value.get())

  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(value.get(), builder)
}
