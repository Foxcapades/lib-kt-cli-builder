package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal abstract class AbstractScalarArgument<T>(
  override val default: T,
  hasDefault: Boolean,
  shouldQuote: Boolean,
  protected val formatter: ArgumentFormatter<T>,
) : AbstractArgument<T>(hasDefault, shouldQuote), ScalarArgument<T> {
  override fun writeToString(builder: CliArgumentAppender) {
    formatter(value.get(), builder)
  }
}