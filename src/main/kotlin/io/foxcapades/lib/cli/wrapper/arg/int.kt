package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface IntArgument : ScalarArgument<Int>

fun intArg(action: ArgOptions<Int>.() -> Unit): IntArgument {
  val opts = ArgOptions(Int::class).also(action)

  return IntArgumentImpl(
    default     = ArgOptions<Int>::default.property(opts),
    isRequired  = ArgOptions<Int>::required.property(opts),
    shouldQuote = ArgOptions<Int>::shouldQuote.property(opts),
    formatter   = ArgOptions<Int>::formatter.property(opts),
    filter      = ArgOptions<Int>::filter.property(opts),
  )
}

fun nullableIntArg(action: NullableArgOptions<Int>.() -> Unit): Argument<Int?> {
  val opts = NullableArgOptions(Int::class).also(action)

  return GeneralArgumentImpl(
    type        = Int::class,
    nullable    = true,
    default     = NullableArgOptions<Int>::default.property(opts),
    shouldQuote = NullableArgOptions<Int>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Int>::required.property(opts),
    formatter   = NullableArgOptions<Int>::formatter.property(opts),
    filter      = NullableArgOptions<Int>::filter.property(opts),
  )
}

internal class IntArgumentImpl : AbstractScalarArgument<IntArgument, Int>, IntArgument {
  constructor(
    default:     Property<Int>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    filter:      Property<ArgumentPredicate<IntArgument, Int>>,
    formatter:   Property<ArgumentFormatter<Int>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Int::toString))
  )
  constructor(isRequired: Boolean) : super(
    default     = Property(),
    isRequired  = isRequired,
    shouldQuote = false,
    filter      = Property(ArgSetFilter.unsafeCast()),
    formatter   = ArgumentFormatter(Int::toString),
  )
}
