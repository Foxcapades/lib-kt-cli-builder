package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface StringArgument : ComplexArgument<String>, ScalarArgument<String>

fun stringArg(action: ArgOptions<String>.() -> Unit): StringArgument {
  val opts = ArgOptions(String::class).also(action)

  return StringArgumentImpl(
    default     = ArgOptions<String>::default.property(opts),
    isRequired  = ArgOptions<String>::requireArg.property(opts),
    shouldQuote = ArgOptions<String>::shouldQuote.property(opts),
    formatter   = ArgOptions<String>::formatter.property(opts),
  )
}

fun nullableStringArg(action: NullableArgOptions<String>.() -> Unit): Argument<String?> {
  val opts = NullableArgOptions(String::class).also(action)

  return GeneralArgumentImpl(
    type        = String::class,
    nullable    = true,
    default     = NullableArgOptions<String>::default.property(opts),
    shouldQuote = NullableArgOptions<String>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<String>::requireArg.property(opts),
    formatter   = NullableArgOptions<String>::formatter.property(opts)
  )
}

internal class StringArgumentImpl : AbstractScalarArgument<String>, StringArgument {
  constructor(
    default:     Property<String>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<String>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(String::toString))
  )

  constructor(default: String, isRequired: Boolean, formatter: ArgumentFormatter<String>)
    : super(default.asProperty(), isRequired, true, formatter)

  constructor(default: String, isRequired: Boolean)
    : super(default.asProperty(), isRequired, true, ArgumentFormatter(String::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<String>)
    : super(Property.empty(), isRequired, true, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, true, ArgumentFormatter(String::toString))
}
