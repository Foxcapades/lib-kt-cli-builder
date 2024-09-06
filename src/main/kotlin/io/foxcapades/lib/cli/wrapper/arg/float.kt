package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface FloatArgument : ScalarArgument<Float>

fun floatArg(action: ArgOptions<Float>.() -> Unit): FloatArgument {
  val opts = ArgOptions(Float::class).also(action)

  return FloatArgumentImpl(
    default     = ArgOptions<Float>::default.property(opts),
    isRequired  = ArgOptions<Float>::required.property(opts),
    shouldQuote = ArgOptions<Float>::shouldQuote.property(opts),
    formatter   = ArgOptions<Float>::formatter.property(opts),
    filter      = ArgOptions<Float>::filter.property(opts),
  )
}

fun nullableFloatArg(action: NullableArgOptions<Float>.() -> Unit): Argument<Float?> {
  val opts = NullableArgOptions(Float::class).also(action)

  return GeneralArgumentImpl(
    type        = Float::class,
    nullable    = true,
    default     = NullableArgOptions<Float>::default.property(opts),
    shouldQuote = NullableArgOptions<Float>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Float>::required.property(opts),
    formatter   = NullableArgOptions<Float>::formatter.property(opts),
    filter      = NullableArgOptions<Float>::filter.property(opts),
  )
}

internal class FloatArgumentImpl : AbstractScalarArgument<FloatArgument, Float>, FloatArgument {
  constructor(
    default:     Property<Float>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Float>>,
    filter:      Property<ArgumentPredicate<FloatArgument, Float>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Float::toString)),
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(Float::toString),
  )
}
