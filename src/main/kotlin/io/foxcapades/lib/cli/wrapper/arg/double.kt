package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface DoubleArgument : ScalarArgument<Double>

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DoubleArgument =
  DoubleArgumentImpl(ArgOptions(Double::class).also(action))

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> =
  GeneralArgumentImpl.of(NullableArgOptions(Double::class).also(action))

internal class DoubleArgumentImpl(
  default:     Property<Double>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Double>>,
  filter:      Property<ArgumentPredicate<DoubleArgument, Double>>,
) : AbstractScalarArgument<DoubleArgument, Double>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  formatter   = formatter.getOr(ArgumentFormatter(Double::toString)),
  filter      = filter,
), DoubleArgument {
  constructor(opts: ArgOptions<Double>) : this(
    default     = ArgOptions<Double>::default.property(opts),
    isRequired  = ArgOptions<Double>::required.property(opts),
    shouldQuote = ArgOptions<Double>::shouldQuote.property(opts),
    formatter   = ArgOptions<Double>::formatter.property(opts),
    filter      = ArgOptions<Double>::filter.property(opts),
  )
}
