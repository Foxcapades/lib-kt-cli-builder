package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.getOrCompute

interface ComplexArgument<T> : Argument<T>

internal open class ComplexArgumentImpl<T>(
  default:     Property<T> = Property.empty(),
  isRequired:  Property<Boolean> = Property.empty(),
  shouldQuote: Property<Boolean> = Property.empty(),
  formatter:   Property<ArgumentFormatter<T>> = Property.empty(),
)
  : AbstractArgument<T>(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(true),
  )
  , ComplexArgument<T>
{
  @Suppress("UNCHECKED_CAST")
  private val formatter = formatter.getOrCompute { NullableGeneralStringifier as ArgumentFormatter<T> }

  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(value.get(), builder)
}
