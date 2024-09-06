package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*

interface ByteArgument : ScalarArgument<Byte>

fun byteArg(action: ArgOptions<Byte>.() -> Unit): ByteArgument =
  ByteArgumentImpl(ArgOptions(Byte::class).also(action))

fun nullableByteArg(action: NullableArgOptions<Byte>.() -> Unit): Argument<Byte?> =
  GeneralArgumentImpl.of(NullableArgOptions(Byte::class).also(action))

internal class ByteArgumentImpl(
  default:     Property<Byte>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<Byte>>,
  filter:      Property<ArgumentPredicate<ByteArgument, Byte>>
) : AbstractScalarArgument<ByteArgument, Byte>(
  default     = default,
  isRequired  = isRequired.getOr(!default.isSet),
  shouldQuote = shouldQuote.getOr(false),
  filter      = filter,
  formatter   = formatter.getOr(ArgumentFormatter(Byte::toString)),
), ByteArgument {
  constructor(opts: ArgOptions<Byte>) : this(
    default     = ArgOptions<Byte>::default.property(opts),
    isRequired  = ArgOptions<Byte>::required.property(opts),
    shouldQuote = ArgOptions<Byte>::shouldQuote.property(opts),
    formatter   = ArgOptions<Byte>::formatter.property(opts),
    filter      = ArgOptions<Byte>::filter.property(opts),
  )
}
