package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface FloatArgument : ScalarArgument<Float>

fun floatArg(action: ArgOptions<Float>.() -> Unit): FloatArgument =
  FloatArgumentImpl(ArgOptions(Float::class).also(action))

fun nullableFloatArg(action: NullableArgOptions<Float>.() -> Unit): Argument<Float?> =
  GeneralArgumentImpl.of(NullableArgOptions(Float::class).also(action))

internal class FloatArgumentImpl(
  default:     Property<Float>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Float>>,
  filter:      Property<ArgumentPredicate<FloatArgument, Float>>,
) : AbstractScalarArgument<FloatArgument, Float>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Float::toString)),
), FloatArgument {
  constructor(opts: ArgOptions<Float>) : this(
    default     = ArgOptions<Float>::default.property(opts),
    isRequired  = ArgOptions<Float>::required.property(opts),
    shouldQuote = ArgOptions<Float>::shouldQuote.property(opts),
    formatter   = ArgOptions<Float>::formatter.property(opts),
    filter      = ArgOptions<Float>::filter.property(opts),
  )
}
