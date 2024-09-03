package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UByteArgs
import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UByteFlags {
  fun optional(longForm: String, shortForm: Char, default: UByte, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, shortForm, false, UByteArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: UByte): UByteFlag
    = UByteFlagImpl(longForm, shortForm, false, UByteArgs.optional(default))

  fun optional(longForm: String, default: UByte, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, false, UByteArgs.optional(default, formatter))

  fun optional(longForm: String, default: UByte): UByteFlag
    = UByteFlagImpl(longForm, false, UByteArgs.optional(default))

  fun optional(shortForm: Char, default: UByte, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(shortForm, false, UByteArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: UByte): UByteFlag
    = UByteFlagImpl(shortForm, false, UByteArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, shortForm, true, UByteArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): UByteFlag
    = UByteFlagImpl(longForm, shortForm, true, UByteArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, true, UByteArgs.required(formatter))

  fun required(longForm: String): UByteFlag
    = UByteFlagImpl(longForm, true, UByteArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<UByte>): UByteFlag
    = UByteFlagImpl(shortForm, true, UByteArgs.required(formatter))

  fun required(shortForm: Char): UByteFlag
    = UByteFlagImpl(shortForm, true, UByteArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(shortForm, isRequired, arg)
}