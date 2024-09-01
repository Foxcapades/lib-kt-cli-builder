package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.FloatArgs
import io.foxcapades.lib.cli.wrapper.arg.FloatArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.FloatFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

object FloatFlags {
  fun optional(longForm: String, shortForm: Byte, default: Float, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, shortForm, false, FloatArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: Float): FloatFlag
    = FloatFlagImpl(longForm, shortForm, false, FloatArgs.optional(default))

  fun optional(longForm: String, default: Float, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, false, FloatArgs.optional(default, formatter))

  fun optional(longForm: String, default: Float): FloatFlag
    = FloatFlagImpl(longForm, false, FloatArgs.optional(default))

  fun optional(shortForm: Byte, default: Float, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(shortForm, false, FloatArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: Float): FloatFlag
    = FloatFlagImpl(shortForm, false, FloatArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, shortForm, true, FloatArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): FloatFlag
    = FloatFlagImpl(longForm, shortForm, true, FloatArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, true, FloatArgs.required(formatter))

  fun required(longForm: String): FloatFlag
    = FloatFlagImpl(longForm, true, FloatArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<Float>): FloatFlag
    = FloatFlagImpl(shortForm, true, FloatArgs.required(formatter))

  fun required(shortForm: Byte): FloatFlag
    = FloatFlagImpl(shortForm, true, FloatArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(shortForm, isRequired, arg)
}