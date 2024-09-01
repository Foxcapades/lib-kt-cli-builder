package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgs
import io.foxcapades.lib.cli.wrapper.arg.BigIntegerArgument
import io.foxcapades.lib.cli.wrapper.impl.flag.BigIntegerFlagImpl
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter
import java.math.BigInteger

object BigIntegerFlags {
  fun optional(longForm: String, shortForm: Byte, default: BigInteger, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(longForm: String, shortForm: Byte, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, false, BigIntegerArgs.optional(default))

  fun optional(longForm: String, default: BigInteger, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(longForm: String, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, false, BigIntegerArgs.optional(default))

  fun optional(shortForm: Byte, default: BigInteger, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, false, BigIntegerArgs.optional(default, formatter))

  fun optional(shortForm: Byte, default: BigInteger): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, false, BigIntegerArgs.optional(default))

  fun required(longForm: String, shortForm: Byte, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, true, BigIntegerArgs.required(formatter))

  fun required(longForm: String, shortForm: Byte): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, true, BigIntegerArgs.required())

  fun required(longForm: String, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, true, BigIntegerArgs.required(formatter))

  fun required(longForm: String): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, true, BigIntegerArgs.required())

  fun required(shortForm: Byte, formatter: ValueFormatter<BigInteger>): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, true, BigIntegerArgs.required(formatter))

  fun required(shortForm: Byte): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, true, BigIntegerArgs.required())

  fun of(longForm: String, shortForm: Byte, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, shortForm, isRequired, arg)

  fun of(longForm: String, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(longForm, isRequired, arg)

  fun of(shortForm: Byte, isRequired: Boolean, arg: BigIntegerArgument): BigIntegerFlag
    = BigIntegerFlagImpl(shortForm, isRequired, arg)
}