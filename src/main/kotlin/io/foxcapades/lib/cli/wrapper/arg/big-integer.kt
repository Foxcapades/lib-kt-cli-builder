package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*
import java.math.BigInteger

interface BigIntegerArgument : ScalarArgument<BigInteger>

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): BigIntegerArgument =
  BigIntegerArgumentImpl(ArgOptions(BigInteger::class).also(action))

fun nullableBigIntegerArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> =
  GeneralArgumentImpl.of(NullableArgOptions(BigInteger::class).also(action))

internal class BigIntegerArgumentImpl(
  default:     Property<BigInteger>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<BigInteger>>,
  filter:      Property<ArgumentPredicate<BigIntegerArgument, BigInteger>>
) : AbstractScalarArgument<BigIntegerArgument, BigInteger>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(BigInteger::toString)),
), BigIntegerArgument {
  constructor(opts: ArgOptions<BigInteger>) : this(
    default     = ArgOptions<BigInteger>::default.property(opts),
    isRequired  = ArgOptions<BigInteger>::required.property(opts),
    shouldQuote = ArgOptions<BigInteger>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigInteger>::formatter.property(opts),
    filter      = ArgOptions<BigInteger>::filter.property(opts),
  )
}

