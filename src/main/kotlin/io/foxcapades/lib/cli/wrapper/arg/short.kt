package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface ShortArgument : ScalarArgument<Short>

fun shortArg(action: ArgOptions<Short>.() -> Unit): ShortArgument {
  val opts = ArgOptions(Short::class).also(action)

  return ShortArgumentImpl(
    default     = ArgOptions<Short>::default.property(opts),
    isRequired  = ArgOptions<Short>::required.property(opts),
    shouldQuote = ArgOptions<Short>::shouldQuote.property(opts),
    formatter   = ArgOptions<Short>::formatter.property(opts),
    filter      = ArgOptions<Short>::filter.property(opts),
  )
}

fun nullableShortArg(action: NullableArgOptions<Short>.() -> Unit): Argument<Short?> {
  val opts = NullableArgOptions(Short::class).also(action)

  return GeneralArgumentImpl(
    type        = Short::class,
    nullable    = true,
    default     = NullableArgOptions<Short>::default.property(opts),
    shouldQuote = NullableArgOptions<Short>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Short>::required.property(opts),
    formatter   = NullableArgOptions<Short>::formatter.property(opts),
    filter      = NullableArgOptions<Short>::filter.property(opts),
  )
}

internal class ShortArgumentImpl : AbstractScalarArgument<ShortArgument, Short>, ShortArgument {
  constructor(
    default:     Property<Short>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Short>>,
    filter:      Property<ArgumentPredicate<ShortArgument, Short>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Short::toString))
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, false, Property(), ArgumentFormatter(Short::toString))
}
