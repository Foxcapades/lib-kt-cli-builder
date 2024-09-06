package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface StringArgument : ComplexArgument<String>, ScalarArgument<String>

fun stringArg(action: ArgOptions<String>.() -> Unit): StringArgument {
  val opts = ArgOptions(String::class).also(action)

  return StringArgumentImpl(
    default     = ArgOptions<String>::default.property(opts),
    isRequired  = ArgOptions<String>::required.property(opts),
    shouldQuote = ArgOptions<String>::shouldQuote.property(opts),
    formatter   = ArgOptions<String>::formatter.property(opts),
    filter      = ArgOptions<String>::filter.property(opts),
  )
}

fun nullableStringArg(action: NullableArgOptions<String>.() -> Unit): Argument<String?> {
  val opts = NullableArgOptions(String::class).also(action)

  return GeneralArgumentImpl(
    type        = String::class,
    nullable    = true,
    default     = NullableArgOptions<String>::default.property(opts),
    shouldQuote = NullableArgOptions<String>::shouldQuote.property<Boolean>(opts).setIfAbsent(true),
    isRequired  = NullableArgOptions<String>::required.property(opts),
    formatter   = NullableArgOptions<String>::formatter.property(opts),
    filter      = NullableArgOptions<String>::filter.property(opts),
  )
}

internal class StringArgumentImpl : AbstractScalarArgument<StringArgument, String>, StringArgument {
  constructor(
    default:     Property<String>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<StringArgument, String>>,
    formatter:   Property<ArgumentFormatter<String>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(true),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(String::toString))
  )

  constructor(isRequired: Boolean)
    : super(Property(), isRequired, true, Property(), ArgumentFormatter(String::toString))
}
