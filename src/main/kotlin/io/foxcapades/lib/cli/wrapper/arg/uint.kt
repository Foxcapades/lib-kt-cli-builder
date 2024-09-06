package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UIntArgument : ScalarArgument<UInt>

fun uintArg(action: ArgOptions<UInt>.() -> Unit): UIntArgument =
  UIntArgumentImpl(ArgOptions(UInt::class).also(action))

fun nullableUIntArg(action: NullableArgOptions<UInt>.() -> Unit): Argument<UInt?> {
  return GeneralArgumentImpl.of(NullableArgOptions(UInt::class).also(action))
}

internal class UIntArgumentImpl(
  default:     Property<UInt>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<UIntArgument, UInt>>,
  formatter:   Property<ArgumentFormatter<UInt>>,
) : AbstractScalarArgument<UIntArgument, UInt>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UInt::toString)),
), UIntArgument {
  constructor(opts: ArgOptions<UInt>) : this(
    default     = ArgOptions<UInt>::default.property(opts),
    isRequired  = ArgOptions<UInt>::required.property(opts),
    shouldQuote = ArgOptions<UInt>::shouldQuote.property(opts),
    formatter   = ArgOptions<UInt>::formatter.property(opts),
    filter      = ArgOptions<UInt>::filter.property(opts),
  )
}
