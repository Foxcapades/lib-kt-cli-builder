package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.impl.flag.LongFlagImpl
import io.foxcapades.lib.cli.wrapper.arg.LongArgs
import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object LongFlags {
  fun optional(longForm: String, shortForm: Byte, default: Long, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, shortForm, false, LongArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Long): LongFlag
    = LongFlagImpl(longForm, shortForm, false, LongArgs.optional(default))

  fun optional(longForm: String, default: Long, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, false, LongArgs.optional(default, formatter))

  fun optional(longForm: String, default: Long): LongFlag
    = LongFlagImpl(longForm, false, LongArgs.optional(default))

  fun optional(shortForm: Byte, default: Long, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(shortForm, false, LongArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Long): LongFlag
    = LongFlagImpl(shortForm, false, LongArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, shortForm, true, LongArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): LongFlag
    = LongFlagImpl(longForm, shortForm, true, LongArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(longForm, true, LongArgs.required(formatter))

  fun required(longForm: String): LongFlag
    = LongFlagImpl(longForm, true, LongArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Long>): LongFlag
    = LongFlagImpl(shortForm, true, LongArgs.required(formatter))

  fun required(shortForm: Byte): LongFlag
    = LongFlagImpl(shortForm, true, LongArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: LongArgument): LongFlag
    = LongFlagImpl(shortForm, isRequired, arg)
}