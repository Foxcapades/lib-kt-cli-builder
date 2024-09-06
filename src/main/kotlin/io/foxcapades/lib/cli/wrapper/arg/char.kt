package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface CharArgument : ScalarArgument<Char>

fun charArg(action: ArgOptions<Char>.() -> Unit): CharArgument {
  val opts = ArgOptions(Char::class).also(action)

  return CharArgumentImpl(
    default     = ArgOptions<Char>::default.property(opts),
    isRequired  = ArgOptions<Char>::required.property(opts),
    shouldQuote = ArgOptions<Char>::shouldQuote.property(opts),
    formatter   = ArgOptions<Char>::formatter.property(opts),
    filter      = ArgOptions<Char>::filter.property(opts),
  )
}

fun nullableCharArg(action: NullableArgOptions<Char>.() -> Unit): Argument<Char?> {
  val opts = NullableArgOptions(Char::class).also(action)

  return GeneralArgumentImpl(
    type        = Char::class,
    nullable    = true,
    default     = NullableArgOptions<Char>::default.property(opts),
    shouldQuote = NullableArgOptions<Char>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Char>::required.property(opts),
    formatter   = NullableArgOptions<Char>::formatter.property(opts),
    filter      = NullableArgOptions<Char>::filter.property(opts),
  )
}

internal class CharArgumentImpl : AbstractScalarArgument<CharArgument, Char>, CharArgument {
  constructor(
    default:     Property<Char>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Char>>,
    filter:      Property<ArgumentPredicate<CharArgument, Char>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Char::toString)),
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(Char::toString),
  )
}
