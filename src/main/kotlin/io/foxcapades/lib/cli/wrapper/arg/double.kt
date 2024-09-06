package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface DoubleArgument : ScalarArgument<Double>

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DoubleArgument {
  val opts = ArgOptions(Double::class).also(action)

  return DoubleArgumentImpl(
    default     = ArgOptions<Double>::default.property(opts),
    isRequired  = ArgOptions<Double>::required.property(opts),
    shouldQuote = ArgOptions<Double>::shouldQuote.property(opts),
    formatter   = ArgOptions<Double>::formatter.property(opts),
    filter      = ArgOptions<Double>::filter.property(opts),
  )
}

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> {
  val opts = NullableArgOptions(Double::class).also(action)

  return GeneralArgumentImpl(
    type        = Double::class,
    nullable    = true,
    default     = NullableArgOptions<Double>::default.property(opts),
    shouldQuote = NullableArgOptions<Double>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Double>::required.property(opts),
    formatter   = NullableArgOptions<Double>::formatter.property(opts),
    filter      = NullableArgOptions<Double>::filter.property(opts),
  )
}

internal class DoubleArgumentImpl : AbstractScalarArgument<DoubleArgument, Double>, DoubleArgument {
  constructor(
    default:     Property<Double>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Double>>,
    filter:      Property<ArgumentPredicate<DoubleArgument, Double>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Double::toString)),
    filter      = filter,
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(Double::toString),
  )
}
