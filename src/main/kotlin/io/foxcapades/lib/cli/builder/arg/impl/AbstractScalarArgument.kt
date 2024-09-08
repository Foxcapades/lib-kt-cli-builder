package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ScalarArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.util.properties.Property

internal abstract class AbstractScalarArgument<out A : ScalarArgument<V>, V>(
  default:     Property<V>,
  isRequired:  Property<Boolean>,
  shouldQuote: Boolean,
  filter:      Property<ArgumentPredicate<A, V>>,
  formatter:   ArgumentFormatter<V>,
)
  : AbstractArgument<A, V>(default, isRequired, shouldQuote, filter, formatter)
  , ScalarArgument<V>
