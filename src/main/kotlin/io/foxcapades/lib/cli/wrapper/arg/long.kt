package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface LongArgument : ScalarArgument<Long>

fun longArg(action: ArgOptions<Long>.() -> Unit): LongArgument =
  LongArgumentImpl(ArgOptions(Long::class).also(action))

fun nullableLongArg(action: NullableArgOptions<Long>.() -> Unit): Argument<Long?> =
  GeneralArgumentImpl.of(NullableArgOptions(Long::class).also(action))

internal class LongArgumentImpl(
  default:     Property<Long>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<LongArgument, Long>>,
  formatter:   Property<ArgumentFormatter<Long>>,
) : AbstractScalarArgument<LongArgument, Long>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Long::toString))
), LongArgument {
  constructor(opts: ArgOptions<Long>) : this(
    default     = ArgOptions<Long>::default.property(opts),
    isRequired  = ArgOptions<Long>::required.property(opts),
    shouldQuote = ArgOptions<Long>::shouldQuote.property(opts),
    formatter   = ArgOptions<Long>::formatter.property(opts),
    filter      = ArgOptions<Long>::filter.property(opts),
  )
}
