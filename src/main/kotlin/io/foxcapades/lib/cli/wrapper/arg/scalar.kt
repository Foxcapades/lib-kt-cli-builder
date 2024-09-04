package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property

interface ScalarArgument<T> : Argument<T>

internal abstract class AbstractScalarArgument<T>(
  default: Property<T>,
  isRequired: Boolean,
  shouldQuote: Boolean,
  protected val formatter: ArgumentFormatter<T>,
) : AbstractArgument<T>(default, isRequired, shouldQuote), ScalarArgument<T> {
  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(value.get(), builder)
}
