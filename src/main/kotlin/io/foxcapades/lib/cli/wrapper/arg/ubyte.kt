package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UByteArgument : ScalarArgument<UByte>

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): UByteArgument =
  UByteArgumentImpl(ArgOptions(UByte::class).also(action))

fun nullableUByteArg(action: NullableArgOptions<UByte>.() -> Unit): Argument<UByte?> =
  GeneralArgumentImpl.of(NullableArgOptions(UByte::class).also(action))

internal class UByteArgumentImpl(
  default:     Property<UByte>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<UByteArgument, UByte>>,
  formatter:   Property<ArgumentFormatter<UByte>>,
) : AbstractScalarArgument<UByteArgument, UByte>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(UByte::toString)),
), UByteArgument {
  constructor(opts: ArgOptions<UByte>) : this(
    default     = ArgOptions<UByte>::default.property(opts),
    isRequired  = ArgOptions<UByte>::required.property(opts),
    shouldQuote = ArgOptions<UByte>::shouldQuote.property(opts),
    formatter   = ArgOptions<UByte>::formatter.property(opts),
    filter      = ArgOptions<UByte>::filter.property(opts),
  )
}
