package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.CharArgs
import io.foxcapades.lib.cli.wrapper.arg.CharArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.CharFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object CharFlags {
  fun optional(longForm: String, shortForm: Byte, default: Char, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(longForm, shortForm, false, CharArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Char): CharFlag
    = CharFlagImpl(longForm, shortForm, false, CharArgs.optional(default))

  fun optional(longForm: String, default: Char, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(longForm, false, CharArgs.optional(default, formatter))

  fun optional(longForm: String, default: Char): CharFlag
    = CharFlagImpl(longForm, false, CharArgs.optional(default))

  fun optional(shortForm: Byte, default: Char, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(shortForm, false, CharArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Char): CharFlag
    = CharFlagImpl(shortForm, false, CharArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(longForm, shortForm, true, CharArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): CharFlag
    = CharFlagImpl(longForm, shortForm, true, CharArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(longForm, true, CharArgs.required(formatter))

  fun required(longForm: String): CharFlag
    = CharFlagImpl(longForm, true, CharArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Char>): CharFlag
    = CharFlagImpl(shortForm, true, CharArgs.required(formatter))

  fun required(shortForm: Byte): CharFlag
    = CharFlagImpl(shortForm, true, CharArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: CharArgument): CharFlag
    = CharFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: CharArgument): CharFlag
    = CharFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: CharArgument): CharFlag
    = CharFlagImpl(shortForm, isRequired, arg)
}