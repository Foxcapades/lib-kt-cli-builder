package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.DoubleArgs
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.DoubleFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object DoubleFlags {
  fun optional(longForm: String, shortForm: Byte, default: Double, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(longForm, shortForm, false, DoubleArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Double): DoubleFlag
    = DoubleFlagImpl(longForm, shortForm, false, DoubleArgs.optional(default))

  fun optional(longForm: String, default: Double, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(longForm, false, DoubleArgs.optional(default, formatter))

  fun optional(longForm: String, default: Double): DoubleFlag
    = DoubleFlagImpl(longForm, false, DoubleArgs.optional(default))

  fun optional(shortForm: Byte, default: Double, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(shortForm, false, DoubleArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Double): DoubleFlag
    = DoubleFlagImpl(shortForm, false, DoubleArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(longForm, shortForm, true, DoubleArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): DoubleFlag
    = DoubleFlagImpl(longForm, shortForm, true, DoubleArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(longForm, true, DoubleArgs.required(formatter))

  fun required(longForm: String): DoubleFlag
    = DoubleFlagImpl(longForm, true, DoubleArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Double>): DoubleFlag
    = DoubleFlagImpl(shortForm, true, DoubleArgs.required(formatter))

  fun required(shortForm: Byte): DoubleFlag
    = DoubleFlagImpl(shortForm, true, DoubleArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: DoubleArgument): DoubleFlag
    = DoubleFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: DoubleArgument): DoubleFlag
    = DoubleFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: DoubleArgument): DoubleFlag
    = DoubleFlagImpl(shortForm, isRequired, arg)
}