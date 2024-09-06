package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface CharArgument : ScalarArgument<Char>

fun charArg(action: ArgOptions<Char>.() -> Unit): CharArgument =
  CharArgumentImpl(ArgOptions(Char::class).also(action))

fun nullableCharArg(action: NullableArgOptions<Char>.() -> Unit): Argument<Char?> =
  GeneralArgumentImpl.of(NullableArgOptions(Char::class).also(action))

internal class CharArgumentImpl(
  default:     Property<Char>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Char>>,
  filter:      Property<ArgumentPredicate<CharArgument, Char>>,
) : AbstractScalarArgument<CharArgument, Char>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Char::toString)),
), CharArgument {
  constructor(opts: ArgOptions<Char>) : this(
    default     = ArgOptions<Char>::default.property(opts),
    isRequired  = ArgOptions<Char>::required.property(opts),
    shouldQuote = ArgOptions<Char>::shouldQuote.property(opts),
    formatter   = ArgOptions<Char>::formatter.property(opts),
    filter      = ArgOptions<Char>::filter.property(opts),
  )
}
