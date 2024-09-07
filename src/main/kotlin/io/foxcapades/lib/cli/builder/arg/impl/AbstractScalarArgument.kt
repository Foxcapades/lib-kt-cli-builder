package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ScalarArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal abstract class AbstractScalarArgument<out A : ScalarArgument<V>, V>(
  default: Property<V>,
  isRequired:  Boolean,
  shouldQuote: Boolean,
  filter: Property<ArgumentPredicate<A, V>>,
  formatter: ArgumentFormatter<V>,
)
  : AbstractArgument<A, V>(default, isRequired, shouldQuote, filter.getOr(ArgSetFilter.unsafeCast()))
  , ScalarArgument<V>
{
  protected val formatter = formatter

  override fun writeToString(builder: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), builder)
}
