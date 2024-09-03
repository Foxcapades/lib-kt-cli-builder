package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ULongArgs
import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ULongFlags {
  fun optional(longForm: String, shortForm: Char, default: ULong, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, shortForm, false, ULongArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: ULong): ULongFlag
    = ULongFlagImpl(longForm, shortForm, false, ULongArgs.optional(default))

  fun optional(longForm: String, default: ULong, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, false, ULongArgs.optional(default, formatter))

  fun optional(longForm: String, default: ULong): ULongFlag
    = ULongFlagImpl(longForm, false, ULongArgs.optional(default))

  fun optional(shortForm: Char, default: ULong, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(shortForm, false, ULongArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: ULong): ULongFlag
    = ULongFlagImpl(shortForm, false, ULongArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, shortForm, true, ULongArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): ULongFlag
    = ULongFlagImpl(longForm, shortForm, true, ULongArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, true, ULongArgs.required(formatter))

  fun required(longForm: String): ULongFlag
    = ULongFlagImpl(longForm, true, ULongArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<ULong>): ULongFlag
    = ULongFlagImpl(shortForm, true, ULongArgs.required(formatter))

  fun required(shortForm: Char): ULongFlag
    = ULongFlagImpl(shortForm, true, ULongArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(shortForm, isRequired, arg)
}