package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface IntArgument : ScalarArgument<Int>

fun intArg(action: ArgOptions<Int>.() -> Unit): IntArgument =
  IntArgumentImpl(ArgOptions(Int::class).also(action))

fun nullableIntArg(action: NullableArgOptions<Int>.() -> Unit): Argument<Int?> =
  GeneralArgumentImpl.of(NullableArgOptions(Int::class).also(action))

internal class IntArgumentImpl(
  default:     Property<Int>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<IntArgument, Int>>,
  formatter:   Property<ArgumentFormatter<Int>>
) : AbstractScalarArgument<IntArgument, Int>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Int::toString))
), IntArgument {
  constructor(opts: ArgOptions<Int>) : this(
    default     = ArgOptions<Int>::default.property(opts),
    isRequired  = ArgOptions<Int>::required.property(opts),
    shouldQuote = ArgOptions<Int>::shouldQuote.property(opts),
    formatter   = ArgOptions<Int>::formatter.property(opts),
    filter      = ArgOptions<Int>::filter.property(opts),
  )
}
