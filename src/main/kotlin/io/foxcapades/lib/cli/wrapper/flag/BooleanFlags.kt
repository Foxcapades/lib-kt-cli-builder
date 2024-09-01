package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BooleanArgs
import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.BooleanFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object BooleanFlags {
  fun toggle(longForm: String, shortForm: Byte): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, false, true, BooleanArgs.optional(false))

  fun toggle(longForm: String): BooleanFlag
    = BooleanFlagImpl(longForm, false, true, BooleanArgs.optional(false))

  fun toggle(shortForm: Byte): BooleanFlag
    = BooleanFlagImpl(shortForm, false, true, BooleanArgs.optional(false))

  fun optional(longForm: String, shortForm: Byte, default: Boolean, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, false, false, BooleanArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Boolean): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, false, false, BooleanArgs.optional(default))

  fun optional(longForm: String, default: Boolean, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(longForm, false, false, BooleanArgs.optional(default, formatter))

  fun optional(longForm: String, default: Boolean): BooleanFlag
    = BooleanFlagImpl(longForm, false, false, BooleanArgs.optional(default))

  fun optional(shortForm: Byte, default: Boolean, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(shortForm, false, false, BooleanArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Boolean): BooleanFlag
    = BooleanFlagImpl(shortForm, false, false, BooleanArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, true, false, BooleanArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, true, false, BooleanArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(longForm, true, false, BooleanArgs.required(formatter))

  fun required(longForm: String): BooleanFlag
    = BooleanFlagImpl(longForm, true, false, BooleanArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Boolean>): BooleanFlag
    = BooleanFlagImpl(shortForm, true, false, BooleanArgs.required(formatter))

  fun required(shortForm: Byte): BooleanFlag
    = BooleanFlagImpl(shortForm, true, false, BooleanArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: BooleanArgument): BooleanFlag
    = BooleanFlagImpl(longForm, shortForm, isRequired, false, arg)

  fun of(longForm: String, isRequired: Boolean, arg: BooleanArgument): BooleanFlag
    = BooleanFlagImpl(longForm, isRequired, false, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: BooleanArgument): BooleanFlag
    = BooleanFlagImpl(shortForm, isRequired, false, arg)
}