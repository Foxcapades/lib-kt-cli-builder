package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.StringArgs
import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object StringFlags {
  fun optional(longForm: String, shortForm: Char, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional("", formatter))

  fun optional(longForm: String, shortForm: Char, default: String, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional(""))

  fun optional(longForm: String, shortForm: Char, default: String): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional(default))

  fun optional(longForm: String, default: String, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(longForm, false, StringArgs.optional(default, formatter))

  fun optional(longForm: String): StringFlag
    = StringFlagImpl(longForm, false, StringArgs.optional(""))

  fun optional(longForm: String, default: String): StringFlag
    = StringFlagImpl(longForm, false, StringArgs.optional(default))

  fun optional(shortForm: Char, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional("", formatter))

  fun optional(shortForm: Char, default: String, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional(default, formatter))

  fun optional(shortForm: Char): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional(""))

  fun optional(shortForm: Char, default: String): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(longForm, shortForm, true, StringArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): StringFlag
    = StringFlagImpl(longForm, shortForm, true, StringArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(longForm, true, StringArgs.required(formatter))

  fun required(longForm: String): StringFlag
    = StringFlagImpl(longForm, true, StringArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<String>): StringFlag
    = StringFlagImpl(shortForm, true, StringArgs.required(formatter))

  fun required(shortForm: Char): StringFlag
    = StringFlagImpl(shortForm, true, StringArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(shortForm, isRequired, arg)
}