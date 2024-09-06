package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface ULongArgument : ScalarArgument<ULong>

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): ULongArgument {
  val opts = ArgOptions(ULong::class).also(action)

  return ULongArgumentImpl(
    default     = ArgOptions<ULong>::default.property(opts),
    isRequired  = ArgOptions<ULong>::required.property(opts),
    shouldQuote = ArgOptions<ULong>::shouldQuote.property(opts),
    formatter   = ArgOptions<ULong>::formatter.property(opts),
    filter      = ArgOptions<ULong>::filter.property(opts),
  )
}

fun nullableULongArg(action: NullableArgOptions<ULong>.() -> Unit): Argument<ULong?> {
  val opts = NullableArgOptions(ULong::class).also(action)

  return GeneralArgumentImpl(
    type        = ULong::class,
    nullable    = true,
    default     = NullableArgOptions<ULong>::default.property(opts),
    shouldQuote = NullableArgOptions<ULong>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<ULong>::required.property(opts),
    formatter   = NullableArgOptions<ULong>::formatter.property(opts),
    filter      = NullableArgOptions<ULong>::filter.property(opts),
  )
}

internal class ULongArgumentImpl : AbstractScalarArgument<ULongArgument, ULong>, ULongArgument {
  constructor(
    default:     Property<ULong>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<ULongArgument, ULong>>,
    formatter:   Property<ArgumentFormatter<ULong>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(ULong::toString)),
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, false, Property(), ArgumentFormatter(ULong::toString))
}
