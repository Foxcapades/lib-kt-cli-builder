package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*
import java.math.BigInteger

interface BigIntegerArgument : ScalarArgument<BigInteger>

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): BigIntegerArgument {
  val opts = ArgOptions(BigInteger::class).also(action)

  return BigIntegerArgumentImpl(
    default     = ArgOptions<BigInteger>::default.property(opts),
    isRequired  = ArgOptions<BigInteger>::required.property(opts),
    shouldQuote = ArgOptions<BigInteger>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigInteger>::formatter.property(opts),
    filter      = ArgOptions<BigInteger>::filter.property(opts),
  )
}

fun nullableBigIntegerArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> {
  val opts = NullableArgOptions(BigInteger::class).also(action)

  return GeneralArgumentImpl(
    type        = BigInteger::class,
    nullable    = true,
    default     = NullableArgOptions<BigInteger>::default.property(opts),
    shouldQuote = NullableArgOptions<BigInteger>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<BigInteger>::required.property(opts),
    formatter   = NullableArgOptions<BigInteger>::formatter.property(opts),
    filter      = NullableArgOptions<BigInteger>::filter.property(opts)
  )
}

internal class BigIntegerArgumentImpl : AbstractScalarArgument<BigIntegerArgument, BigInteger>, BigIntegerArgument {
  constructor(
    default:     Property<BigInteger>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<BigInteger>>,
    filter:      Property<ArgumentPredicate<BigIntegerArgument, BigInteger>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(BigInteger::toString)),
  )

  constructor(isRequired: Boolean) : super(
    default     = Property(),
    isRequired  = isRequired,
    shouldQuote = false,
    filter      = Property(ArgSetFilter.unsafeCast()),
    formatter   = ArgumentFormatter(BigInteger::toString),
  )
}

