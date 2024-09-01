package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgs
import io.foxcapades.lib.cli.wrapper.arg.BigDecimalArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.BigDecimalFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigDecimal

object BigDecimalFlags {
  fun optional(longForm: String, shortForm: Byte, default: BigDecimal, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, false, BigDecimalArgs.optional(default))

  fun optional(longForm: String, default: BigDecimal, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(longForm: String, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, false, BigDecimalArgs.optional(default))

  fun optional(shortForm: Byte, default: BigDecimal, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, false, BigDecimalArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: BigDecimal): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, false, BigDecimalArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, true, BigDecimalArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, true, BigDecimalArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, true, BigDecimalArgs.required(formatter))

  fun required(longForm: String): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, true, BigDecimalArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<BigDecimal>): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, true, BigDecimalArgs.required(formatter))

  fun required(shortForm: Byte): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, true, BigDecimalArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: BigDecimalArgument): BigDecimalFlag
    = BigDecimalFlagImpl(shortForm, isRequired, arg)
}
