package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.impl.flag.MultiFlagImpl
import io.foxcapades.lib.cli.wrapper.arg.MultiArgs
import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object MultiFlags {
  fun <T> optional(
    longForm: String,
    shortForm: Byte,
    default: Iterable<T>,
    formatter: ValueFormatter<Iterable<T>>
  ): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, false, MultiArgs.optional(default, formatter))

  fun <T> optional(longForm: String, default: Iterable<T>, formatter: ValueFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, false, MultiArgs.optional(default, formatter))

  fun <T> optional(shortForm: Byte, default: Iterable<T>, formatter: ValueFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(shortForm, false, MultiArgs.optional(default, formatter))

  fun <T> required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, true, MultiArgs.required(formatter))

  fun <T> required(longForm: String, formatter: ValueFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(longForm, true, MultiArgs.required(formatter))

  fun <T> required(shortForm: Byte, formatter: ValueFormatter<Iterable<T>>): MultiFlag<T>
    = MultiFlagImpl(shortForm, true, MultiArgs.required(formatter))

  fun <T> of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(longForm, shortForm, isRequired, arg)

  fun <T> of(longForm: String, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(longForm, isRequired, arg)

  fun <T> of(shortForm: Byte, isRequired: Boolean, arg: MultiArgument<T>): MultiFlag<T>
    = MultiFlagImpl(shortForm, isRequired, arg)
}