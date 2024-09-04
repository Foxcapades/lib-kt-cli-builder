package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface IntArgument : ScalarArgument<Int>

fun intArg(action: ArgOptions<Int>.() -> Unit): IntArgument {
  val opts = ArgOptions(Int::class).also(action)

  return IntArgumentImpl(
    default     = ArgOptions<Int>::default.property(opts),
    isRequired  = ArgOptions<Int>::requireArg.property(opts),
    shouldQuote = ArgOptions<Int>::shouldQuote.property(opts),
    formatter   = ArgOptions<Int>::formatter.property(opts),
  )
}

fun nullableIntArg(action: NullableArgOptions<Int>.() -> Unit): Argument<Int?> {
  val opts = NullableArgOptions(Int::class).also(action)

  return GeneralArgumentImpl(
    type        = Int::class,
    nullable    = true,
    default     = NullableArgOptions<Int>::default.property(opts),
    shouldQuote = NullableArgOptions<Int>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Int>::requireArg.property(opts),
    formatter   = NullableArgOptions<Int>::formatter.property(opts)
  )
}

internal class IntArgumentImpl : AbstractScalarArgument<Int>, IntArgument {
  constructor(
    default:     Property<Int>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Int>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Int::toString))
  )

  constructor(default: Int, isRequired: Boolean, formatter: ArgumentFormatter<Int>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Int, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Int::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Int>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Int::toString))
}
