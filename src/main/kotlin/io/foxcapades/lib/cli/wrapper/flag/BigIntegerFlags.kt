package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgs
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import java.math.BigInteger

object BigIntegerFlags {
  fun optional(longForm: String, shortForm: Char, default: BigInteger, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Char, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, false, BigIntegerArgs.optional(default))

  fun optional(longForm: String, default: BigInteger, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(longForm: String, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, false, BigIntegerArgs.optional(default))

  fun optional(shortForm: Char, default: BigInteger, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(shortForm: Char, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, false, BigIntegerArgs.optional(default))

  fun required(longForm: String, shortForm: Char, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, true, BigIntegerArgs.required(formatter))

  fun required(longForm: String, shortForm: Char): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, true, BigIntegerArgs.required())

  fun required(longForm: String, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, true, BigIntegerArgs.required(formatter))

  fun required(longForm: String): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, true, BigIntegerArgs.required())

  fun required(shortForm: Char, formatter: ArgumentFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, true, BigIntegerArgs.required(formatter))

  fun required(shortForm: Char): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, true, BigIntegerArgs.required())

  fun of(longForm: String, shortForm: Char, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Char, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, isRequired, arg)
}
