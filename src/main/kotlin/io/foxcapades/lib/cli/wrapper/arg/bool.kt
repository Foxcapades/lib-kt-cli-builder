package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface BooleanArgument : ScalarArgument<Boolean>

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): BooleanArgument {
  val opts = ArgOptions(Boolean::class).also(action)

  return BooleanArgumentImpl(
    default     = ArgOptions<Boolean>::default.property(opts),
    isRequired  = ArgOptions<Boolean>::required.property(opts),
    shouldQuote = ArgOptions<Boolean>::shouldQuote.property(opts),
    formatter   = ArgOptions<Boolean>::formatter.property(opts),
    filter      = ArgOptions<Boolean>::filter.property(opts),
  )
}

fun nullableBooleanArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> {
  val opts = NullableArgOptions(Boolean::class).also(action)

  return GeneralArgumentImpl(
    type        = Boolean::class,
    nullable    = true,
    default     = NullableArgOptions<Boolean>::default.property(opts),
    shouldQuote = NullableArgOptions<Boolean>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Boolean>::required.property(opts),
    formatter   = NullableArgOptions<Boolean>::formatter.property(opts),
    filter      = NullableArgOptions<Boolean>::filter.property(opts),
  )
}

internal class BooleanArgumentImpl : AbstractScalarArgument<BooleanArgument, Boolean>, BooleanArgument {
  constructor(
    default:     Property<Boolean>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Boolean>>,
    filter:      Property<ArgumentPredicate<BooleanArgument, Boolean>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Boolean::toString)),
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(Boolean::toString),
  )
}
