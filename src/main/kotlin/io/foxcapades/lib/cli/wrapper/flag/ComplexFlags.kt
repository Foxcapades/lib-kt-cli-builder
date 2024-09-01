package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ComplexArgs
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.ComplexFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ComplexFlags {
  fun <T> optional(
    longForm: String,
    shortForm: Byte,
    default: T,
    formatter: ValueFormatter<T>
  ): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, false, ComplexArgs.optional(default, formatter))

  fun <T> optional(longForm: String, default: T, formatter: ValueFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, false, ComplexArgs.optional(default, formatter))

  fun <T> optional(shortForm: Byte, default: T, formatter: ValueFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, false, ComplexArgs.optional(default, formatter))

  fun <T> required(longForm: String, shortForm: Byte, formatter: ValueFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, true, ComplexArgs.required(formatter))

  fun <T> required(longForm: String, formatter: ValueFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, true, ComplexArgs.required(formatter))

  fun <T> required(shortForm: Byte, formatter: ValueFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, true, ComplexArgs.required(formatter))

  fun <T> of(
    longForm: String,
    shortForm: Byte,
    isRequired: Boolean,
    arg: ComplexArgument<T>
  ): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, isRequired, arg)

  fun <T> of(longForm: String, isRequired: Boolean, arg: ComplexArgument<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, isRequired, arg)

  fun <T> of(shortForm: Byte, isRequired: Boolean, arg: ComplexArgument<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, isRequired, arg)
}