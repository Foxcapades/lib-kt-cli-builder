package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.IntArgs
import io.foxcapades.lib.cli.wrapper.arg.IntArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object IntFlags {
  fun optional(longForm: String, shortForm: Char, default: Int, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(longForm, shortForm, false, IntArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: Int): IntFlag
    = IntFlagImpl(longForm, shortForm, false, IntArgs.optional(default))

  fun optional(longForm: String, default: Int, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(longForm, false, IntArgs.optional(default, formatter))

  fun optional(longForm: String, default: Int): IntFlag
    = IntFlagImpl(longForm, false, IntArgs.optional(default))

  fun optional(shortForm: Char, default: Int, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(shortForm, false, IntArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: Int): IntFlag
    = IntFlagImpl(shortForm, false, IntArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(longForm, shortForm, true, IntArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): IntFlag
    = IntFlagImpl(longForm, shortForm, true, IntArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(longForm, true, IntArgs.required(formatter))

  fun required(longForm: String): IntFlag
    = IntFlagImpl(longForm, true, IntArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<Int>): IntFlag
    = IntFlagImpl(shortForm, true, IntArgs.required(formatter))

  fun required(shortForm: Char): IntFlag
    = IntFlagImpl(shortForm, true, IntArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: IntArgument): IntFlag
    = IntFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: IntArgument): IntFlag
    = IntFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: IntArgument): IntFlag
    = IntFlagImpl(shortForm, isRequired, arg)
}