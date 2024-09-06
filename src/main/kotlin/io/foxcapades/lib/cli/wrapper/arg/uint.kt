package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface UIntArgument : ScalarArgument<UInt>

fun uintArg(action: ArgOptions<UInt>.() -> Unit): UIntArgument {
  val opts = ArgOptions(UInt::class).also(action)

  return UIntArgumentImpl(
    default     = ArgOptions<UInt>::default.property(opts),
    isRequired  = ArgOptions<UInt>::required.property(opts),
    shouldQuote = ArgOptions<UInt>::shouldQuote.property(opts),
    formatter   = ArgOptions<UInt>::formatter.property(opts),
    filter      = ArgOptions<UInt>::filter.property(opts),
  )
}

fun nullableUIntArg(action: NullableArgOptions<UInt>.() -> Unit): Argument<UInt?> {
  val opts = NullableArgOptions(UInt::class).also(action)

  return GeneralArgumentImpl(
    type        = UInt::class,
    nullable    = true,
    default     = NullableArgOptions<UInt>::default.property(opts),
    shouldQuote = NullableArgOptions<UInt>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<UInt>::required.property(opts),
    formatter   = NullableArgOptions<UInt>::formatter.property(opts),
    filter      = NullableArgOptions<UInt>::filter.property(opts),
  )
}

internal class UIntArgumentImpl : AbstractScalarArgument<UIntArgument, UInt>, UIntArgument {
  constructor(
    default:     Property<UInt>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<UIntArgument, UInt>>,
    formatter:   Property<ArgumentFormatter<UInt>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(UInt::toString)),
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, false, Property(), ArgumentFormatter(UInt::toString))
}
