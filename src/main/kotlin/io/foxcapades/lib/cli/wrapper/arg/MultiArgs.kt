package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object MultiArgs {
  @JvmStatic
  fun <T> required(formatter: ArgumentFormatter<Iterable<T>>): MultiArgument<T>
    = MultiArgumentImpl(formatter)

  @JvmStatic
  fun <T> optional(default: Iterable<T>, formatter: ArgumentFormatter<Iterable<T>>): MultiArgument<T>
    = MultiArgumentImpl(default, formatter)
}