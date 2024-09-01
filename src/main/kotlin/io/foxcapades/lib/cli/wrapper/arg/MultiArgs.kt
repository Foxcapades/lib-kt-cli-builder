package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.MultiArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object MultiArgs {
  @JvmStatic
  fun <T> required(formatter: ValueFormatter<Iterable<T>>): MultiArgument<T>
    = MultiArgumentImpl(formatter)

  @JvmStatic
  fun <T> optional(default: Iterable<T>, formatter: ValueFormatter<Iterable<T>>): MultiArgument<T>
    = MultiArgumentImpl(default, formatter)
}