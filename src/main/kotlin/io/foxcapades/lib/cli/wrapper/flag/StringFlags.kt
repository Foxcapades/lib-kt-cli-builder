package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.StringArgs
import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.StringFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object StringFlags {
  fun optional(longForm: String, shortForm: Byte, default: String, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: String): StringFlag
    = StringFlagImpl(longForm, shortForm, false, StringArgs.optional(default))

  fun optional(longForm: String, default: String, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(longForm, false, StringArgs.optional(default, formatter))

  fun optional(longForm: String, default: String): StringFlag
    = StringFlagImpl(longForm, false, StringArgs.optional(default))

  fun optional(shortForm: Byte, default: String, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: String): StringFlag
    = StringFlagImpl(shortForm, false, StringArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(longForm, shortForm, true, StringArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): StringFlag
    = StringFlagImpl(longForm, shortForm, true, StringArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(longForm, true, StringArgs.required(formatter))

  fun required(longForm: String): StringFlag
    = StringFlagImpl(longForm, true, StringArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<String>): StringFlag
    = StringFlagImpl(shortForm, true, StringArgs.required(formatter))

  fun required(shortForm: Byte): StringFlag
    = StringFlagImpl(shortForm, true, StringArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: StringArgument): StringFlag
    = StringFlagImpl(shortForm, isRequired, arg)
}