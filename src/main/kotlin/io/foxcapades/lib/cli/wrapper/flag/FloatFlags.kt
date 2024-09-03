package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.FloatArgs
import io.foxcapades.lib.cli.wrapper.arg.FloatArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter

object FloatFlags {
  fun optional(longForm: String, shortForm: Char, default: Float, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, shortForm, false, FloatArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: Float): FloatFlag
    = FloatFlagImpl(longForm, shortForm, false, FloatArgs.optional(default))

  fun optional(longForm: String, default: Float, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, false, FloatArgs.optional(default, formatter))

  fun optional(longForm: String, default: Float): FloatFlag
    = FloatFlagImpl(longForm, false, FloatArgs.optional(default))

  fun optional(shortForm: Char, default: Float, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(shortForm, false, FloatArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: Float): FloatFlag
    = FloatFlagImpl(shortForm, false, FloatArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, shortForm, true, FloatArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): FloatFlag
    = FloatFlagImpl(longForm, shortForm, true, FloatArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(longForm, true, FloatArgs.required(formatter))

  fun required(longForm: String): FloatFlag
    = FloatFlagImpl(longForm, true, FloatArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<Float>): FloatFlag
    = FloatFlagImpl(shortForm, true, FloatArgs.required(formatter))

  fun required(shortForm: Char): FloatFlag
    = FloatFlagImpl(shortForm, true, FloatArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: FloatArgument): FloatFlag
    = FloatFlagImpl(shortForm, isRequired, arg)
}