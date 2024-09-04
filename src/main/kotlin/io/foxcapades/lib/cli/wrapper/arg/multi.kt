package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.Property

interface MultiArgument<T> : ComplexArgument<Iterable<T>>

internal class MultiArgumentImpl<T>(
  default:     Property<Iterable<T>> = Property.empty(),
  isRequired:  Property<Boolean> = Property.empty(),
  shouldQuote: Property<Boolean> = Property.empty(),
  formatter  : Property<ArgumentFormatter<Iterable<T>>> = Property.empty()
)
  : ComplexArgumentImpl<Iterable<T>>(default, isRequired, shouldQuote, formatter)
  , MultiArgument<T>
