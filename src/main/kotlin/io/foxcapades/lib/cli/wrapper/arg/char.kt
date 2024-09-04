package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface CharArgument : ScalarArgument<Char>

fun charArg(action: ArgOptions<Char>.() -> Unit): CharArgument {
  val opts = ArgOptions(Char::class).also(action)

  return CharArgumentImpl(
    default     = ArgOptions<Char>::default.property(opts),
    isRequired  = ArgOptions<Char>::requireArg.property(opts),
    shouldQuote = ArgOptions<Char>::shouldQuote.property(opts),
    formatter   = ArgOptions<Char>::formatter.property(opts),
  )
}

fun nullableCharArg(action: NullableArgOptions<Char>.() -> Unit): Argument<Char?> {
  val opts = NullableArgOptions(Char::class).also(action)

  return GeneralArgumentImpl(
    type        = Char::class,
    nullable    = true,
    default     = NullableArgOptions<Char>::default.property(opts),
    shouldQuote = NullableArgOptions<Char>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Char>::requireArg.property(opts),
    formatter   = NullableArgOptions<Char>::formatter.property(opts)
  )
}

internal class CharArgumentImpl : AbstractScalarArgument<Char>, CharArgument {
  constructor(
    default:     Property<Char>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Char>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Char::toString))
  )

  constructor(default: Char, isRequired: Boolean, formatter: ArgumentFormatter<Char>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Char, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Char::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Char>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Char::toString))
}
