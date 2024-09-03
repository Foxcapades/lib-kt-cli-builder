package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgs
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import java.math.BigDecimal

object BigDecimalFlags {
  fun optional(longForm: String, shortForm: Char, default: BigDecimal, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, false, BigDecimalArgs.optional(default))

  fun optional(longForm: String, default: BigDecimal, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(longForm: String, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, false, BigDecimalArgs.optional(default))

  fun optional(shortForm: Char, default: BigDecimal, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, false, BigDecimalArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, true, BigDecimalArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, true, BigDecimalArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, true, BigDecimalArgs.required(formatter))

  fun required(longForm: String): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, true, BigDecimalArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, true, BigDecimalArgs.required(formatter))

  fun required(shortForm: Char): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, true, BigDecimalArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, isRequired, arg)
}
