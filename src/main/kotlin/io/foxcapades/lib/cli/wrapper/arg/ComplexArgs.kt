package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ComplexArgs {
  @JvmStatic
  fun <T> required(formatter: ArgumentFormatter<T>): ComplexArgument<T>
    = ComplexArgumentImpl(formatter)

  @JvmStatic
  fun <T> optional(default: T, formatter: ArgumentFormatter<T>): ComplexArgument<T>
    = ComplexArgumentImpl(default, formatter)
}