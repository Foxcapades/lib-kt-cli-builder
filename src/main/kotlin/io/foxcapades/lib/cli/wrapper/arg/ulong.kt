package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface ULongArgument : ScalarArgument<ULong>

object ULongArgs {
  @JvmStatic
  fun required(): ULongArgument =
    ULongArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<ULong>): ULongArgument =
    ULongArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): ULongArgument =
    ULongArgumentImpl(false)

  @JvmStatic
  fun optional(default: ULong): ULongArgument =
    ULongArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<ULong>): ULongArgument =
    ULongArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: ULong, formatter: ArgumentFormatter<ULong>): ULongArgument =
    ULongArgumentImpl(default, false, formatter)
}

fun ulongArg(action: ArgOptions<ULong>.() -> Unit): ULongArgument {
  val opts = ArgOptions(ULong::class).also(action)

  return ULongArgumentImpl(
    default     = ArgOptions<ULong>::default.property(opts),
    isRequired  = ArgOptions<ULong>::requireArg.property(opts),
    shouldQuote = ArgOptions<ULong>::shouldQuote.property(opts),
    formatter   = ArgOptions<ULong>::formatter.property(opts),
  )
}

fun nullableULongArg(action: NullableArgOptions<ULong>.() -> Unit): Argument<ULong?> {
  val opts = NullableArgOptions(ULong::class).also(action)

  return GeneralArgumentImpl(
    type        = ULong::class,
    nullable    = true,
    default     = NullableArgOptions<ULong>::default.property(opts),
    shouldQuote = NullableArgOptions<ULong>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<ULong>::requireArg.property(opts),
    formatter   = NullableArgOptions<ULong>::formatter.property(opts)
  )
}

internal class ULongArgumentImpl : AbstractScalarArgument<ULong>, ULongArgument {
  constructor(
    default:     Property<ULong>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<ULong>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(ULong::toString))
  )

  constructor(default: ULong, isRequired: Boolean, formatter: ArgumentFormatter<ULong>)
    : super(default.asProperty(), true, false, formatter)

  constructor(default: ULong, isRequired: Boolean)
    : super(default.asProperty(), true, false, ArgumentFormatter(ULong::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<ULong>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(ULong::toString))
}
