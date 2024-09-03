package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ShortArgs
import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ShortFlags {
  fun optional(longForm: String, shortForm: Char, default: Short, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, shortForm, false, ShortArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: Short): ShortFlag
    = ShortFlagImpl(longForm, shortForm, false, ShortArgs.optional(default))

  fun optional(longForm: String, default: Short, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, false, ShortArgs.optional(default, formatter))

  fun optional(longForm: String, default: Short): ShortFlag
    = ShortFlagImpl(longForm, false, ShortArgs.optional(default))

  fun optional(shortForm: Char, default: Short, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(shortForm, false, ShortArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: Short): ShortFlag
    = ShortFlagImpl(shortForm, false, ShortArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, shortForm, true, ShortArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): ShortFlag
    = ShortFlagImpl(longForm, shortForm, true, ShortArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, true, ShortArgs.required(formatter))

  fun required(longForm: String): ShortFlag
    = ShortFlagImpl(longForm, true, ShortArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<Short>): ShortFlag
    = ShortFlagImpl(shortForm, true, ShortArgs.required(formatter))

  fun required(shortForm: Char): ShortFlag
    = ShortFlagImpl(shortForm, true, ShortArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(shortForm, isRequired, arg)
}