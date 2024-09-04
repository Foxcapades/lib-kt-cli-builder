package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface DoubleArgument : ScalarArgument<Double>

object DoubleArgs {
  @JvmStatic
  fun required(): DoubleArgument =
    DoubleArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Double>): DoubleArgument =
    DoubleArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): DoubleArgument =
    DoubleArgumentImpl(false)

  @JvmStatic
  fun optional(default: Double): DoubleArgument =
    DoubleArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<Double>): DoubleArgument =
    DoubleArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: Double, formatter: ArgumentFormatter<Double>): DoubleArgument =
    DoubleArgumentImpl(default, false, formatter)
}

fun doubleArg(action: ArgOptions<Double>.() -> Unit): DoubleArgument {
  val opts = ArgOptions(Double::class).also(action)

  return DoubleArgumentImpl(
    default     = ArgOptions<Double>::default.property(opts),
    isRequired  = ArgOptions<Double>::requireArg.property(opts),
    shouldQuote = ArgOptions<Double>::shouldQuote.property(opts),
    formatter   = ArgOptions<Double>::formatter.property(opts),
  )
}

fun nullableDoubleArg(action: NullableArgOptions<Double>.() -> Unit): Argument<Double?> {
  val opts = NullableArgOptions(Double::class).also(action)

  return GeneralArgumentImpl(
    type        = Double::class,
    nullable    = true,
    default     = NullableArgOptions<Double>::default.property(opts),
    shouldQuote = NullableArgOptions<Double>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Double>::requireArg.property(opts),
    formatter   = NullableArgOptions<Double>::formatter.property(opts)
  )
}

internal class DoubleArgumentImpl : AbstractScalarArgument<Double>, DoubleArgument {
  constructor(
    default:     Property<Double>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Double>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Double::toString))
  )

  constructor(default: Double, isRequired: Boolean, formatter: ArgumentFormatter<Double>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Double, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Double::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Double>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Double::toString))
}
