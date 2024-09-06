package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UShortArgument : ScalarArgument<UShort>

fun ushortArg(action: ArgOptions<UShort>.() -> Unit): UShortArgument {
  val opts = ArgOptions(UShort::class).also(action)

  return UShortArgumentImpl(
    default     = ArgOptions<UShort>::default.property(opts),
    isRequired  = ArgOptions<UShort>::required.property(opts),
    shouldQuote = ArgOptions<UShort>::shouldQuote.property(opts),
    formatter   = ArgOptions<UShort>::formatter.property(opts),
    filter      = ArgOptions<UShort>::filter.property(opts),
  )
}

fun nullableUShortArg(action: NullableArgOptions<UShort>.() -> Unit): Argument<UShort?> {
  val opts = NullableArgOptions(UShort::class).also(action)

  return GeneralArgumentImpl(
    type        = UShort::class,
    nullable    = true,
    default     = NullableArgOptions<UShort>::default.property(opts),
    shouldQuote = NullableArgOptions<UShort>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<UShort>::required.property(opts),
    formatter   = NullableArgOptions<UShort>::formatter.property(opts),
    filter      = NullableArgOptions<UShort>::filter.property(opts),
  )
}

internal class UShortArgumentImpl : AbstractScalarArgument<UShortArgument, UShort>, UShortArgument {
  constructor(
    default:     Property<UShort>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<UShortArgument, UShort>>,
    formatter:   Property<ArgumentFormatter<UShort>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(UShort::toString)),
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, false, Property(), ArgumentFormatter(UShort::toString))
}
