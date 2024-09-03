package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UIntArgs
import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object UIntFlags {
  fun optional(longForm: String, shortForm: Char, default: UInt, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, shortForm, false, UIntArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: UInt): UIntFlag
    = UIntFlagImpl(longForm, shortForm, false, UIntArgs.optional(default))

  fun optional(longForm: String, default: UInt, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, false, UIntArgs.optional(default, formatter))

  fun optional(longForm: String, default: UInt): UIntFlag
    = UIntFlagImpl(longForm, false, UIntArgs.optional(default))

  fun optional(shortForm: Char, default: UInt, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(shortForm, false, UIntArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: UInt): UIntFlag
    = UIntFlagImpl(shortForm, false, UIntArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, shortForm, true, UIntArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): UIntFlag
    = UIntFlagImpl(longForm, shortForm, true, UIntArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(longForm, true, UIntArgs.required(formatter))

  fun required(longForm: String): UIntFlag
    = UIntFlagImpl(longForm, true, UIntArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<UInt>): UIntFlag
    = UIntFlagImpl(shortForm, true, UIntArgs.required(formatter))

  fun required(shortForm: Char): UIntFlag
    = UIntFlagImpl(shortForm, true, UIntArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: UIntArgument): UIntFlag
    = UIntFlagImpl(shortForm, isRequired, arg)
}