package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface LongArgument : ScalarArgument<Long>

fun longArg(action: ArgOptions<Long>.() -> Unit): LongArgument {
  val opts = ArgOptions(Long::class).also(action)

  return LongArgumentImpl(
    default     = ArgOptions<Long>::default.property(opts),
    isRequired  = ArgOptions<Long>::required.property(opts),
    shouldQuote = ArgOptions<Long>::shouldQuote.property(opts),
    formatter   = ArgOptions<Long>::formatter.property(opts),
    filter      = ArgOptions<Long>::filter.property(opts),
  )
}

fun nullableLongArg(action: NullableArgOptions<Long>.() -> Unit): Argument<Long?> {
  val opts = NullableArgOptions(Long::class).also(action)

  return GeneralArgumentImpl(
    type        = Long::class,
    nullable    = true,
    default     = NullableArgOptions<Long>::default.property(opts),
    shouldQuote = NullableArgOptions<Long>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Long>::required.property(opts),
    formatter   = NullableArgOptions<Long>::formatter.property(opts),
    filter      = NullableArgOptions<Long>::filter.property(opts),
  )
}

internal class LongArgumentImpl : AbstractScalarArgument<LongArgument, Long>, LongArgument {
  constructor(
    default:     Property<Long>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<LongArgument, Long>>,
    formatter:   Property<ArgumentFormatter<Long>>,
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Long::toString))
  )

  constructor(isRequired: Boolean) : super(
    default     = Property(),
    isRequired  = isRequired,
    shouldQuote = false,
    filter      = Property(ArgSetFilter.unsafeCast()),
    formatter   = ArgumentFormatter(Long::toString),
  )
}
