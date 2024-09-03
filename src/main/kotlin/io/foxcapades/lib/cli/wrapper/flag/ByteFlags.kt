package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ByteArgs
import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object ByteFlags {
  fun optional(longForm: String, shortForm: Char, default: Byte, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, shortForm, false, ByteArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: Byte): ByteFlag
    = ByteFlagImpl(longForm, shortForm, false, ByteArgs.optional(default))

  fun optional(longForm: String, default: Byte, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, false, ByteArgs.optional(default, formatter))

  fun optional(longForm: String, default: Byte): ByteFlag
    = ByteFlagImpl(longForm, false, ByteArgs.optional(default))

  fun optional(shortForm: Char, default: Byte, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(shortForm, false, ByteArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: Byte): ByteFlag
    = ByteFlagImpl(shortForm, false, ByteArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, shortForm, true, ByteArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): ByteFlag
    = ByteFlagImpl(longForm, shortForm, true, ByteArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(longForm, true, ByteArgs.required(formatter))

  fun required(longForm: String): ByteFlag
    = ByteFlagImpl(longForm, true, ByteArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<Byte>): ByteFlag
    = ByteFlagImpl(shortForm, true, ByteArgs.required(formatter))

  fun required(shortForm: Char): ByteFlag
    = ByteFlagImpl(shortForm, true, ByteArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: ByteArgument): ByteFlag
    = ByteFlagImpl(shortForm, isRequired, arg)
}