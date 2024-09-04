package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface UByteArgument : ScalarArgument<UByte>

object UByteArgs {
  @JvmStatic
  fun required(): UByteArgument =
    UByteArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<UByte>): UByteArgument =
    UByteArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): UByteArgument =
    UByteArgumentImpl(false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<UByte>): UByteArgument =
    UByteArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: UByte): UByteArgument =
    UByteArgumentImpl(default, false)

  @JvmStatic
  fun optional(default: UByte, formatter: ArgumentFormatter<UByte>): UByteArgument =
    UByteArgumentImpl(default, false, formatter)
}

fun ubyteArg(action: ArgOptions<UByte>.() -> Unit): UByteArgument {
  val opts = ArgOptions(UByte::class).also(action)

  return UByteArgumentImpl(
    default     = ArgOptions<UByte>::default.property(opts),
    isRequired  = ArgOptions<UByte>::requireArg.property(opts),
    shouldQuote = ArgOptions<UByte>::shouldQuote.property(opts),
    formatter   = ArgOptions<UByte>::formatter.property(opts),
  )
}

fun nullableUByteArg(action: NullableArgOptions<UByte>.() -> Unit): Argument<UByte?> {
  val opts = NullableArgOptions(UByte::class).also(action)

  return GeneralArgumentImpl(
    type        = UByte::class,
    nullable    = true,
    default     = NullableArgOptions<UByte>::default.property(opts),
    shouldQuote = NullableArgOptions<UByte>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<UByte>::requireArg.property(opts),
    formatter   = NullableArgOptions<UByte>::formatter.property(opts)
  )
}

internal class UByteArgumentImpl : AbstractScalarArgument<UByte>, UByteArgument {
  constructor(
    default:     Property<UByte>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<UByte>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(UByte::toString))
  )

  constructor(default: UByte, isRequired: Boolean, formatter: ArgumentFormatter<UByte>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: UByte, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(UByte::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<UByte>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(UByte::toString))
}
