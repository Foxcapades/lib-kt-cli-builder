package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UShortArgs
import io.foxcapades.lib.cli.wrapper.arg.UShortArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UShortFlags {
  fun optional(longForm: String, shortForm: Char, default: UShort, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, shortForm, false, UShortArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: UShort): UShortFlag
    = UShortFlagImpl(longForm, shortForm, false, UShortArgs.optional(default))

  fun optional(longForm: String, default: UShort, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, false, UShortArgs.optional(default, formatter))

  fun optional(longForm: String, default: UShort): UShortFlag
    = UShortFlagImpl(longForm, false, UShortArgs.optional(default))

  fun optional(shortForm: Char, default: UShort, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(shortForm, false, UShortArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: UShort): UShortFlag
    = UShortFlagImpl(shortForm, false, UShortArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, shortForm, true, UShortArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): UShortFlag
    = UShortFlagImpl(longForm, shortForm, true, UShortArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, true, UShortArgs.required(formatter))

  fun required(longForm: String): UShortFlag
    = UShortFlagImpl(longForm, true, UShortArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<UShort>): UShortFlag
    = UShortFlagImpl(shortForm, true, UShortArgs.required(formatter))

  fun required(shortForm: Char): UShortFlag
    = UShortFlagImpl(shortForm, true, UShortArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(shortForm, isRequired, arg)
}
