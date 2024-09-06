package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.Property

interface MultiArgument<T> : ComplexArgument<Iterable<T>>

internal class MultiArgumentImpl<V>(
  default:     Property<Iterable<V>> = Property.empty(),
  isRequired:  Property<Boolean> = Property.empty(),
  shouldQuote: Property<Boolean> = Property.empty(),
  filter:      Property<ArgumentPredicate<MultiArgument<V>, Iterable<V>>> = Property.empty(),
  formatter  : Property<ArgumentFormatter<Iterable<V>>> = Property.empty()
)
  : ComplexArgumentImpl<MultiArgument<V>, Iterable<V>>(
    default,
    isRequired,
    shouldQuote,
    formatter,
    filter,
  )
  , MultiArgument<V>
