package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.LongArgs
import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object LongFlags {
  fun optional(longForm: String, shortForm: Char, default: Long, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, shortForm, false, LongArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: Long): LongFlag
    = LongFlagImpl(longForm, shortForm, false, LongArgs.optional(default))

  fun optional(longForm: String, default: Long, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, false, LongArgs.optional(default, formatter))

  fun optional(longForm: String, default: Long): LongFlag
    = LongFlagImpl(longForm, false, LongArgs.optional(default))

  fun optional(shortForm: Char, default: Long, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(shortForm, false, LongArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: Long): LongFlag
    = LongFlagImpl(shortForm, false, LongArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, shortForm, true, LongArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): LongFlag
    = LongFlagImpl(longForm, shortForm, true, LongArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, true, LongArgs.required(formatter))

  fun required(longForm: String): LongFlag
    = LongFlagImpl(longForm, true, LongArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<Long>): LongFlag
    = LongFlagImpl(shortForm, true, LongArgs.required(formatter))

  fun required(shortForm: Char): LongFlag
    = LongFlagImpl(shortForm, true, LongArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(shortForm, isRequired, arg)
}