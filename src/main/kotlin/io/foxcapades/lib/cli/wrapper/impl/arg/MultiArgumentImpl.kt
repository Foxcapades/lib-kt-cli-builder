package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class MultiArgumentImpl<T> : ComplexArgumentImpl<Iterable<T>>, MultiArgument<T> {
  constructor(default: Iterable<T>, formatter: ValueFormatter<Iterable<T>>) : super(default, formatter)

  constructor(formatter: ValueFormatter<Iterable<T>>) : super(formatter)
}