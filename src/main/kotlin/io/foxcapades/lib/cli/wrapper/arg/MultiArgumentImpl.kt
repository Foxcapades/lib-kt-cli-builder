package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

internal class MultiArgumentImpl<T> : ComplexArgumentImpl<Iterable<T>>, MultiArgument<T> {
  constructor(default: Iterable<T>, formatter: ArgumentFormatter<Iterable<T>>) : super(default, formatter)

  constructor(formatter: ArgumentFormatter<Iterable<T>>) : super(formatter)
}