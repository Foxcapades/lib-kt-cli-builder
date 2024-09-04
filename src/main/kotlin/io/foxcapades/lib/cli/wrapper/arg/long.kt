package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface LongArgument : ScalarArgument<Long>

fun longArg(action: ArgOptions<Long>.() -> Unit): LongArgument {
  val opts = ArgOptions(Long::class).also(action)

  return LongArgumentImpl(
    default     = ArgOptions<Long>::default.property(opts),
    isRequired  = ArgOptions<Long>::requireArg.property(opts),
    shouldQuote = ArgOptions<Long>::shouldQuote.property(opts),
    formatter   = ArgOptions<Long>::formatter.property(opts),
  )
}

fun nullableLongArg(action: NullableArgOptions<Long>.() -> Unit): Argument<Long?> {
  val opts = NullableArgOptions(Long::class).also(action)

  return GeneralArgumentImpl(
    type        = Long::class,
    nullable    = true,
    default     = NullableArgOptions<Long>::default.property(opts),
    shouldQuote = NullableArgOptions<Long>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Long>::requireArg.property(opts),
    formatter   = NullableArgOptions<Long>::formatter.property(opts)
  )
}

internal class LongArgumentImpl : AbstractScalarArgument<Long>, LongArgument {
  constructor(
    default:     Property<Long>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Long>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Long::toString))
  )

  constructor(default: Long, isRequired: Boolean, formatter: ArgumentFormatter<Long>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Long, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Long::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Long>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Long::toString))
}
