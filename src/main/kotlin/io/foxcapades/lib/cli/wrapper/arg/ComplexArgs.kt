package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.impl.arg.ComplexArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ComplexArgs {
  @JvmStatic
  fun <T> required(formatter: ValueFormatter<T>): ComplexArgument<T>
    = ComplexArgumentImpl(formatter)

  @JvmStatic
  fun <T> optional(default: T, formatter: ValueFormatter<T>): ComplexArgument<T>
    = ComplexArgumentImpl(default, formatter)
}