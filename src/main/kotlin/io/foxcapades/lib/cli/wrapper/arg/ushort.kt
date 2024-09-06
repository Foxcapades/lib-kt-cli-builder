package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UShortArgument : ScalarArgument<UShort>

fun ushortArg(action: ArgOptions<UShort>.() -> Unit): UShortArgument =
  UShortArgumentImpl(ArgOptions(UShort::class).also(action))

fun nullableUShortArg(action: NullableArgOptions<UShort>.() -> Unit): Argument<UShort?> =
  GeneralArgumentImpl.of(NullableArgOptions(UShort::class).also(action))

internal class UShortArgumentImpl(
  default:     Property<UShort>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<UShortArgument, UShort>>,
  formatter:   Property<ArgumentFormatter<UShort>>,
) : AbstractScalarArgument<UShortArgument, UShort>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UShort::toString)),
), UShortArgument {
  constructor(opts: ArgOptions<UShort>) : this(
    default     = ArgOptions<UShort>::default.property(opts),
    isRequired  = ArgOptions<UShort>::required.property(opts),
    shouldQuote = ArgOptions<UShort>::shouldQuote.property(opts),
    formatter   = ArgOptions<UShort>::formatter.property(opts),
    filter      = ArgOptions<UShort>::filter.property(opts),
  )
}
