package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgs
import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object MultiFlags {
  fun <T> optional(
    longForm: String,
    shortForm: Char,
    default: Iterable<T>,
    formatter: ArgumentFormatter<Iterable<T>>
  ): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, false, MultiArgs.optional(default, formatter))

  fun <T> optional(longForm: String, default: Iterable<T>, formatter: ArgumentFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, false, MultiArgs.optional(default, formatter))

  fun <T> optional(shortForm: Char, default: Iterable<T>, formatter: ArgumentFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(shortForm, false, MultiArgs.optional(default, formatter))

  fun <T> required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, true, MultiArgs.required(formatter))

  fun <T> required(longForm: String, formatter: ArgumentFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, true, MultiArgs.required(formatter))

  fun <T> required(shortForm: Char, formatter: ArgumentFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(shortForm, true, MultiArgs.required(formatter))

  fun <T> of(longForm: String, shortForm: Char, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, isRequired, arg)

  fun <T> of(longForm: String, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(longForm, isRequired, arg)

  fun <T> of(shortForm: Char, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(shortForm, isRequired, arg)
}