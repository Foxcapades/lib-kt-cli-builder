package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.*

interface ByteArgument : ScalarArgument<Byte>

fun byteArg(action: ArgOptions<Byte>.() -> Unit): ByteArgument {
  val opts = ArgOptions(Byte::class).also(action)

  return ByteArgumentImpl(
    default     = ArgOptions<Byte>::default.property(opts),
    isRequired  = ArgOptions<Byte>::required.property(opts),
    shouldQuote = ArgOptions<Byte>::shouldQuote.property(opts),
    formatter   = ArgOptions<Byte>::formatter.property(opts),
    filter      = ArgOptions<Byte>::filter.property(opts),
  )
}

fun nullableByteArg(action: NullableArgOptions<Byte>.() -> Unit): Argument<Byte?> {
  val opts = NullableArgOptions(Byte::class).also(action)

  return GeneralArgumentImpl(
    type        = Byte::class,
    nullable    = true,
    default     = NullableArgOptions<Byte>::default.property(opts),
    shouldQuote = NullableArgOptions<Byte>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Byte>::required.property(opts),
    formatter   = NullableArgOptions<Byte>::formatter.property(opts),
    filter      = NullableArgOptions<Byte>::filter.property(opts),
  )
}

internal class ByteArgumentImpl : AbstractScalarArgument<ByteArgument, Byte>, ByteArgument {
  constructor(
    default:     Property<Byte>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Byte>>,
    filter:      Property<ArgumentPredicate<ByteArgument, Byte>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    filter      = filter,
    formatter   = formatter.getOr(ArgumentFormatter(Byte::toString)),
  )

  constructor(isRequired: Boolean) : super(
    Property(),
    isRequired,
    false,
    Property(ArgSetFilter.unsafeCast()),
    ArgumentFormatter(Byte::toString),
  )
}
