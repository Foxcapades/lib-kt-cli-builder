package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface FloatArgument : ScalarArgument<Float>

object FloatArgs {
  @JvmStatic
  fun required(): FloatArgument =
    FloatArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Float>): FloatArgument =
    FloatArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): FloatArgument =
    FloatArgumentImpl(false)

  @JvmStatic
  fun optional(default: Float): FloatArgument =
    FloatArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<Float>): FloatArgument =
    FloatArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: Float, formatter: ArgumentFormatter<Float>): FloatArgument =
    FloatArgumentImpl(default, false, formatter)
}

fun floatArg(action: ArgOptions<Float>.() -> Unit): FloatArgument {
  val opts = ArgOptions(Float::class).also(action)

  return FloatArgumentImpl(
    default     = ArgOptions<Float>::default.property(opts),
    isRequired  = ArgOptions<Float>::requireArg.property(opts),
    shouldQuote = ArgOptions<Float>::shouldQuote.property(opts),
    formatter   = ArgOptions<Float>::formatter.property(opts),
  )
}

fun nullableFloatArg(action: NullableArgOptions<Float>.() -> Unit): Argument<Float?> {
  val opts = NullableArgOptions(Float::class).also(action)

  return GeneralArgumentImpl(
    type        = Float::class,
    nullable    = true,
    default     = NullableArgOptions<Float>::default.property(opts),
    shouldQuote = NullableArgOptions<Float>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Float>::requireArg.property(opts),
    formatter   = NullableArgOptions<Float>::formatter.property(opts)
  )
}

internal class FloatArgumentImpl : AbstractScalarArgument<Float>, FloatArgument {
  constructor(
    default:     Property<Float>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Float>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Float::toString))
  )

  constructor(default: Float, isRequired: Boolean, formatter: ArgumentFormatter<Float>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Float, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Float::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Float>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Float::toString))
}
