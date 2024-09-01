package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UIntArgs
import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.UIntFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object UIntFlags {
  fun optional(longForm: String, shortForm: Byte, default: UInt, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, shortForm, false, UIntArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: UInt): UIntFlag
    = UIntFlagImpl(longForm, shortForm, false, UIntArgs.optional(default))

  fun optional(longForm: String, default: UInt, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, false, UIntArgs.optional(default, formatter))

  fun optional(longForm: String, default: UInt): UIntFlag
    = UIntFlagImpl(longForm, false, UIntArgs.optional(default))

  fun optional(shortForm: Byte, default: UInt, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(shortForm, false, UIntArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: UInt): UIntFlag
    = UIntFlagImpl(shortForm, false, UIntArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, shortForm, true, UIntArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): UIntFlag
    = UIntFlagImpl(longForm, shortForm, true, UIntArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, true, UIntArgs.required(formatter))

  fun required(longForm: String): UIntFlag
    = UIntFlagImpl(longForm, true, UIntArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<UInt>): UIntFlag
    = UIntFlagImpl(shortForm, true, UIntArgs.required(formatter))

  fun required(shortForm: Byte): UIntFlag
    = UIntFlagImpl(shortForm, true, UIntArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(shortForm, isRequired, arg)
}