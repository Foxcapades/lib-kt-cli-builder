package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface StringArgument : ComplexArgument<String>, ScalarArgument<String>

fun stringArg(action: ArgOptions<String>.() -> Unit): StringArgument =
  StringArgumentImpl(ArgOptions(String::class).also(action))

fun nullableStringArg(action: NullableArgOptions<String>.() -> Unit): Argument<String?> =
  GeneralArgumentImpl.of(NullableArgOptions(String::class).also {
    it.action()
    NullableArgOptions<String>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

internal class StringArgumentImpl(
  default:     Property<String>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<StringArgument, String>>,
  formatter:   Property<ArgumentFormatter<String>>
) : AbstractScalarArgument<StringArgument, String>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(true),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(String::toString))
), StringArgument {
  constructor(opts: ArgOptions<String>) : this(
    default     = ArgOptions<String>::default.property(opts),
    isRequired  = ArgOptions<String>::required.property(opts),
    shouldQuote = ArgOptions<String>::shouldQuote.property(opts),
    formatter   = ArgOptions<String>::formatter.property(opts),
    filter      = ArgOptions<String>::filter.property(opts),
  )
}
