package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ShortArgs
import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.ShortFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ShortFlags {
  fun optional(longForm: String, shortForm: Byte, default: Short, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, shortForm, false, ShortArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Short): ShortFlag
    = ShortFlagImpl(longForm, shortForm, false, ShortArgs.optional(default))

  fun optional(longForm: String, default: Short, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, false, ShortArgs.optional(default, formatter))

  fun optional(longForm: String, default: Short): ShortFlag
    = ShortFlagImpl(longForm, false, ShortArgs.optional(default))

  fun optional(shortForm: Byte, default: Short, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(shortForm, false, ShortArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Short): ShortFlag
    = ShortFlagImpl(shortForm, false, ShortArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, shortForm, true, ShortArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): ShortFlag
    = ShortFlagImpl(longForm, shortForm, true, ShortArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(longForm, true, ShortArgs.required(formatter))

  fun required(longForm: String): ShortFlag
    = ShortFlagImpl(longForm, true, ShortArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Short>): ShortFlag
    = ShortFlagImpl(shortForm, true, ShortArgs.required(formatter))

  fun required(shortForm: Byte): ShortFlag
    = ShortFlagImpl(shortForm, true, ShortArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: ShortArgument): ShortFlag
    = ShortFlagImpl(shortForm, isRequired, arg)
}