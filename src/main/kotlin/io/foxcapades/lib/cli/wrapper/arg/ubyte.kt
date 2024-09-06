package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UByteArgument : ScalarArgument<UByte>

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): UByteArgument {
  val opts = ArgOptions(UByte::class).also(action)

  return UByteArgumentImpl(
    default     = ArgOptions<UByte>::default.property(opts),
    isRequired  = ArgOptions<UByte>::required.property(opts),
    shouldQuote = ArgOptions<UByte>::shouldQuote.property(opts),
    formatter   = ArgOptions<UByte>::formatter.property(opts),
    filter      = ArgOptions<UByte>::filter.property(opts),
  )
}

fun nullableUByteArg(action: NullableArgOptions<UByte>.() -> Unit): Argument<UByte?> {
  val opts = NullableArgOptions(UByte::class).also(action)

  return GeneralArgumentImpl(
    type        = UByte::class,
    nullable    = true,
    default     = NullableArgOptions<UByte>::default.property(opts),
    shouldQuote = NullableArgOptions<UByte>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<UByte>::required.property(opts),
    formatter   = NullableArgOptions<UByte>::formatter.property(opts),
    filter      = NullableArgOptions<UByte>::filter.property(opts),
  )
}

internal class UByteArgumentImpl : AbstractScalarArgument<UByteArgument, UByte>, UByteArgument {
  constructor(
    default:     Property<UByte>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<UByteArgument, UByte>>,
    formatter:   Property<ArgumentFormatter<UByte>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(UByte::toString)),
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, false, Property(), ArgumentFormatter(UByte::toString))
}
