package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOrNull

interface ScalarArgument<T> : Argument<T>

internal abstract class AbstractScalarArgument<out A : ScalarArgument<V>, V>(
  default:     Property<V>,
  isRequired:  Boolean,
  shouldQuote: Boolean,
  filter:      Property<ArgumentPredicate<A, V>>,
  formatter:   ArgumentFormatter<V>,
)
  : AbstractArgument<A, V>(default, isRequired, shouldQuote, filter.getOrNull() ?: ArgSetFilter.unsafeCast())
  , ScalarArgument<V>
{
  protected val formatter = formatter

  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(get(), builder)
}
