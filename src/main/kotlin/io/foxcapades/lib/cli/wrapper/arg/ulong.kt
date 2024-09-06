package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface ULongArgument : ScalarArgument<ULong>

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): ULongArgument =
  ULongArgumentImpl(ArgOptions(ULong::class).also(action))

fun nullableULongArg(action: NullableArgOptions<ULong>.() -> Unit): Argument<ULong?> =
  GeneralArgumentImpl.of(NullableArgOptions(ULong::class).also(action))

internal class ULongArgumentImpl(
  default:     Property<ULong>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<ULongArgument, ULong>>,
  formatter:   Property<ArgumentFormatter<ULong>>,
) : AbstractScalarArgument<ULongArgument, ULong>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(ULong::toString)),
), ULongArgument {
  constructor(opts: ArgOptions<ULong>) : this(
    default     = ArgOptions<ULong>::default.property(opts),
    isRequired  = ArgOptions<ULong>::required.property(opts),
    shouldQuote = ArgOptions<ULong>::shouldQuote.property(opts),
    formatter   = ArgOptions<ULong>::formatter.property(opts),
    filter      = ArgOptions<ULong>::filter.property(opts),
  )
}
