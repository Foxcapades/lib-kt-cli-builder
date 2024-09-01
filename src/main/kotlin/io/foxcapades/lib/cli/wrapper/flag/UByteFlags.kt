package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UByteArgs
import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.UByteFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UByteFlags {
  fun optional(longForm: String, shortForm: Byte, default: UByte, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, shortForm, false, UByteArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: UByte): UByteFlag
    = UByteFlagImpl(longForm, shortForm, false, UByteArgs.optional(default))

  fun optional(longForm: String, default: UByte, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, false, UByteArgs.optional(default, formatter))

  fun optional(longForm: String, default: UByte): UByteFlag
    = UByteFlagImpl(longForm, false, UByteArgs.optional(default))

  fun optional(shortForm: Byte, default: UByte, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(shortForm, false, UByteArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: UByte): UByteFlag
    = UByteFlagImpl(shortForm, false, UByteArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, shortForm, true, UByteArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): UByteFlag
    = UByteFlagImpl(longForm, shortForm, true, UByteArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(longForm, true, UByteArgs.required(formatter))

  fun required(longForm: String): UByteFlag
    = UByteFlagImpl(longForm, true, UByteArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<UByte>): UByteFlag
    = UByteFlagImpl(shortForm, true, UByteArgs.required(formatter))

  fun required(shortForm: Byte): UByteFlag
    = UByteFlagImpl(shortForm, true, UByteArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: UByteArgument): UByteFlag
    = UByteFlagImpl(shortForm, isRequired, arg)
}