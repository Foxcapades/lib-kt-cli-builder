package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ULongArgs
import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.ULongFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ULongFlags {
  fun optional(longForm: String, shortForm: Byte, default: ULong, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, shortForm, false, ULongArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: ULong): ULongFlag
    = ULongFlagImpl(longForm, shortForm, false, ULongArgs.optional(default))

  fun optional(longForm: String, default: ULong, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, false, ULongArgs.optional(default, formatter))

  fun optional(longForm: String, default: ULong): ULongFlag
    = ULongFlagImpl(longForm, false, ULongArgs.optional(default))

  fun optional(shortForm: Byte, default: ULong, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(shortForm, false, ULongArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: ULong): ULongFlag
    = ULongFlagImpl(shortForm, false, ULongArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, shortForm, true, ULongArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): ULongFlag
    = ULongFlagImpl(longForm, shortForm, true, ULongArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(longForm, true, ULongArgs.required(formatter))

  fun required(longForm: String): ULongFlag
    = ULongFlagImpl(longForm, true, ULongArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<ULong>): ULongFlag
    = ULongFlagImpl(shortForm, true, ULongArgs.required(formatter))

  fun required(shortForm: Byte): ULongFlag
    = ULongFlagImpl(shortForm, true, ULongArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: ULongArgument): ULongFlag
    = ULongFlagImpl(shortForm, isRequired, arg)
}