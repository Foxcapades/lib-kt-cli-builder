package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ComplexArgs
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ComplexFlags {
  fun <T> optional(
    longForm: String,
    shortForm: Char,
    default: T,
    formatter: ArgumentFormatter<T>
  ): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, false, ComplexArgs.optional(default, formatter))

  fun <T> optional(longForm: String, default: T, formatter: ArgumentFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, false, ComplexArgs.optional(default, formatter))

  fun <T> optional(shortForm: Char, default: T, formatter: ArgumentFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, false, ComplexArgs.optional(default, formatter))

  fun <T> required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, true, ComplexArgs.required(formatter))

  fun <T> required(longForm: String, formatter: ArgumentFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, true, ComplexArgs.required(formatter))

  fun <T> required(shortForm: Char, formatter: ArgumentFormatter<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, true, ComplexArgs.required(formatter))

  fun <T> of(
    longForm: String,
    shortForm: Char,
    isRequired: Boolean,
    arg: ComplexArgument<T>
  ): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, shortForm, isRequired, arg)

  fun <T> of(longForm: String, isRequired: Boolean, arg: ComplexArgument<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(longForm, isRequired, arg)

  fun <T> of(shortForm: Char, isRequired: Boolean, arg: ComplexArgument<T>): ComplexFlag<ComplexArgument<T>, T>
    = ComplexFlagImpl(shortForm, isRequired, arg)
}