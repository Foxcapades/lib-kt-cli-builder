package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface UIntArgument : ScalarArgument<UInt>

object UIntArgs {
  @JvmStatic
  fun required(): UIntArgument =
    UIntArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<UInt>): UIntArgument =
    UIntArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): UIntArgument =
    UIntArgumentImpl(false)

  @JvmStatic
  fun optional(default: UInt): UIntArgument
    = UIntArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<UInt>): UIntArgument =
    UIntArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: UInt, formatter: ArgumentFormatter<UInt>): UIntArgument =
    UIntArgumentImpl(default, false, formatter)
}

fun uintArg(action: ArgOptions<UInt>.() -> Unit): UIntArgument {
  val opts = ArgOptions(UInt::class).also(action)

  return UIntArgumentImpl(
    default     = ArgOptions<UInt>::default.property(opts),
    isRequired  = ArgOptions<UInt>::requireArg.property(opts),
    shouldQuote = ArgOptions<UInt>::shouldQuote.property(opts),
    formatter   = ArgOptions<UInt>::formatter.property(opts),
  )
}

fun nullableUIntArg(action: NullableArgOptions<UInt>.() -> Unit): Argument<UInt?> {
  val opts = NullableArgOptions(UInt::class).also(action)

  return GeneralArgumentImpl(
    type        = UInt::class,
    nullable    = true,
    default     = NullableArgOptions<UInt>::default.property(opts),
    shouldQuote = NullableArgOptions<UInt>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<UInt>::requireArg.property(opts),
    formatter   = NullableArgOptions<UInt>::formatter.property(opts)
  )
}

internal class UIntArgumentImpl : AbstractScalarArgument<UInt>, UIntArgument {
  constructor(
    default:     Property<UInt>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<UInt>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(UInt::toString))
  )

  constructor(default: UInt, isRequired: Boolean, formatter: ArgumentFormatter<UInt>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: UInt, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(UInt::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<UInt>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(UInt::toString))
}
