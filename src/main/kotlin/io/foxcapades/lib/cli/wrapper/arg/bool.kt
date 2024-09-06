package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface BooleanArgument : ScalarArgument<Boolean>

fun booleanArg(action: ArgOptions<Boolean>.() -> Unit): BooleanArgument =
  BooleanArgumentImpl(ArgOptions(Boolean::class).also(action))

fun nullableBooleanArg(action: NullableArgOptions<Boolean>.() -> Unit): Argument<Boolean?> =
  GeneralArgumentImpl.of(NullableArgOptions(Boolean::class).also(action))

internal class BooleanArgumentImpl(
  default:     Property<Boolean>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Boolean>>,
  filter:      Property<ArgumentPredicate<BooleanArgument, Boolean>>,
) : AbstractScalarArgument<BooleanArgument, Boolean>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Boolean::toString)),
), BooleanArgument {
  constructor(opts: ArgOptions<Boolean>) : this(
    default     = ArgOptions<Boolean>::default.property(opts),
    isRequired  = ArgOptions<Boolean>::required.property(opts),
    shouldQuote = ArgOptions<Boolean>::shouldQuote.property(opts),
    formatter   = ArgOptions<Boolean>::formatter.property(opts),
    filter      = ArgOptions<Boolean>::filter.property(opts),
  )
}
