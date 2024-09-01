package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ByteArgs
import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.ByteFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object ByteFlags {
  fun optional(longForm: String, shortForm: Byte, default: Byte, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, shortForm, false, ByteArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Byte): ByteFlag
    = ByteFlagImpl(longForm, shortForm, false, ByteArgs.optional(default))

  fun optional(longForm: String, default: Byte, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, false, ByteArgs.optional(default, formatter))

  fun optional(longForm: String, default: Byte): ByteFlag
    = ByteFlagImpl(longForm, false, ByteArgs.optional(default))

  fun optional(shortForm: Byte, default: Byte, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(shortForm, false, ByteArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Byte): ByteFlag
    = ByteFlagImpl(shortForm, false, ByteArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, shortForm, true, ByteArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): ByteFlag
    = ByteFlagImpl(longForm, shortForm, true, ByteArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, true, ByteArgs.required(formatter))

  fun required(longForm: String): ByteFlag
    = ByteFlagImpl(longForm, true, ByteArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Byte>): ByteFlag
    = ByteFlagImpl(shortForm, true, ByteArgs.required(formatter))

  fun required(shortForm: Byte): ByteFlag
    = ByteFlagImpl(shortForm, true, ByteArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(shortForm, isRequired, arg)
}