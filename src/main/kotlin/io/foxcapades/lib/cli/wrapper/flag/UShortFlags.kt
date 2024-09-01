package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UShortArgs
import io.foxcapades.lib.cli.wrapper.arg.UShortArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.UShortFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UShortFlags {
  fun optional(longForm: String, shortForm: Byte, default: UShort, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, shortForm, false, UShortArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: UShort): UShortFlag
    = UShortFlagImpl(longForm, shortForm, false, UShortArgs.optional(default))

  fun optional(longForm: String, default: UShort, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, false, UShortArgs.optional(default, formatter))

  fun optional(longForm: String, default: UShort): UShortFlag
    = UShortFlagImpl(longForm, false, UShortArgs.optional(default))

  fun optional(shortForm: Byte, default: UShort, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(shortForm, false, UShortArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: UShort): UShortFlag
    = UShortFlagImpl(shortForm, false, UShortArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, shortForm, true, UShortArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): UShortFlag
    = UShortFlagImpl(longForm, shortForm, true, UShortArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(longForm, true, UShortArgs.required(formatter))

  fun required(longForm: String): UShortFlag
    = UShortFlagImpl(longForm, true, UShortArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<UShort>): UShortFlag
    = UShortFlagImpl(shortForm, true, UShortArgs.required(formatter))

  fun required(shortForm: Byte): UShortFlag
    = UShortFlagImpl(shortForm, true, UShortArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: UShortArgument): UShortFlag
    = UShortFlagImpl(shortForm, isRequired, arg)
}