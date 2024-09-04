package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*
import java.math.BigInteger

interface BigIntegerArgument : ScalarArgument<BigInteger>

fun bigIntegerArg(action: ArgOptions<BigInteger>.() -> Unit): BigIntegerArgument {
  val opts = ArgOptions(BigInteger::class).also(action)

  return BigIntegerArgumentImpl(
    default     = ArgOptions<BigInteger>::default.property(opts),
    isRequired  = ArgOptions<BigInteger>::requireArg.property(opts),
    shouldQuote = ArgOptions<BigInteger>::shouldQuote.property(opts),
    formatter   = ArgOptions<BigInteger>::formatter.property(opts),
  )
}

fun nullableBigIntegerArg(action: NullableArgOptions<BigInteger>.() -> Unit): Argument<BigInteger?> {
  val opts = NullableArgOptions(BigInteger::class).also(action)

  return GeneralArgumentImpl(
    type        = BigInteger::class,
    nullable    = true,
    default     = NullableArgOptions<BigInteger>::default.property(opts),
    shouldQuote = NullableArgOptions<BigInteger>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<BigInteger>::requireArg.property(opts),
    formatter   = NullableArgOptions<BigInteger>::formatter.property(opts)
  )
}

internal class BigIntegerArgumentImpl : AbstractScalarArgument<BigInteger>, BigIntegerArgument {
  constructor(
    default:     Property<BigInteger>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<BigInteger>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(BigInteger::toString))
  )

  constructor(default: BigInteger, isRequired: Boolean, formatter: ArgumentFormatter<BigInteger>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: BigInteger, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(BigInteger::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<BigInteger>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), false, false, ArgumentFormatter(BigInteger::toString))
}

