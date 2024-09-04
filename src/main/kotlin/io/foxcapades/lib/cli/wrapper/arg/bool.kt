package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface BooleanArgument : ScalarArgument<Boolean>

object BooleanArgs {
  @JvmStatic
  fun required(): BooleanArgument =
    BooleanArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Boolean>): BooleanArgument =
    BooleanArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): BooleanArgument =
    BooleanArgumentImpl(false)

  @JvmStatic
  fun optional(default: Boolean): BooleanArgument =
    BooleanArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<Boolean>): BooleanArgument =
    BooleanArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: Boolean, formatter: ArgumentFormatter<Boolean>): BooleanArgument =
    BooleanArgumentImpl(default, false, formatter)
}

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): BooleanArgument {
  val opts = ArgOptions(Boolean::class).also(action)

  return BooleanArgumentImpl(
    default     = ArgOptions<Boolean>::default.property(opts),
    isRequired  = ArgOptions<Boolean>::requireArg.property(opts),
    shouldQuote = ArgOptions<Boolean>::shouldQuote.property(opts),
    formatter   = ArgOptions<Boolean>::formatter.property(opts),
  )
}

fun nullableBooleanArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> {
  val opts = NullableArgOptions(Boolean::class).also(action)

  return GeneralArgumentImpl(
    type        = Boolean::class,
    nullable    = true,
    default     = NullableArgOptions<Boolean>::default.property(opts),
    shouldQuote = NullableArgOptions<Boolean>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Boolean>::requireArg.property(opts),
    formatter   = NullableArgOptions<Boolean>::formatter.property(opts)
  )
}

internal class BooleanArgumentImpl : AbstractScalarArgument<Boolean>, BooleanArgument {
  constructor(
    default:     Property<Boolean>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Boolean>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Boolean::toString))
  )

  constructor(default: Boolean, isRequired: Boolean, formatter: ArgumentFormatter<Boolean>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Boolean, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Boolean::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Boolean>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Boolean::toString))
}
