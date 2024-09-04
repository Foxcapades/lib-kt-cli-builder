package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.util.*

interface ByteArgument : ScalarArgument<Byte>

object ByteArgs {
  @JvmStatic
  fun required(): ByteArgument =
    ByteArgumentImpl(true)

  @JvmStatic
  fun required(formatter: ArgumentFormatter<Byte>): ByteArgument =
    ByteArgumentImpl(true, formatter)

  @JvmStatic
  fun optional(): ByteArgument =
    ByteArgumentImpl(false)

  @JvmStatic
  fun optional(default: Byte): ByteArgument =
    ByteArgumentImpl(default, false)

  @JvmStatic
  fun optional(formatter: ArgumentFormatter<Byte>): ByteArgument =
    ByteArgumentImpl(false, formatter)

  @JvmStatic
  fun optional(default: Byte, formatter: ArgumentFormatter<Byte>): ByteArgument =
    ByteArgumentImpl(default, false, formatter)
}

fun byteArg(action: ArgOptions<Byte>.() -> Unit): ByteArgument {
  val opts = ArgOptions(Byte::class).also(action)

  return ByteArgumentImpl(
    default     = ArgOptions<Byte>::default.property(opts),
    isRequired  = ArgOptions<Byte>::requireArg.property(opts),
    shouldQuote = ArgOptions<Byte>::shouldQuote.property(opts),
    formatter   = ArgOptions<Byte>::formatter.property(opts),
  )
}

fun nullableByteArg(action: NullableArgOptions<Byte>.() -> Unit): Argument<Byte?> {
  val opts = NullableArgOptions(Byte::class).also(action)

  return GeneralArgumentImpl(
    type        = Byte::class,
    nullable    = true,
    default     = NullableArgOptions<Byte>::default.property(opts),
    shouldQuote = NullableArgOptions<Byte>::shouldQuote.property<Boolean>(opts).setIfAbsent(false),
    isRequired  = NullableArgOptions<Byte>::requireArg.property(opts),
    formatter   = NullableArgOptions<Byte>::formatter.property(opts)
  )
}

internal class ByteArgumentImpl : AbstractScalarArgument<Byte>, ByteArgument {
  constructor(
    default:     Property<Byte>,
    isRequired:  Property<Boolean>,
    shouldQuote: Property<Boolean>,
    formatter:   Property<ArgumentFormatter<Byte>>
  ) : super(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(false),
    formatter   = formatter.getOr(ArgumentFormatter(Byte::toString))
  )

  constructor(default: Byte, isRequired: Boolean, formatter: ArgumentFormatter<Byte>)
    : super(default.asProperty(), isRequired, false, formatter)

  constructor(default: Byte, isRequired: Boolean)
    : super(default.asProperty(), isRequired, false, ArgumentFormatter(Byte::toString))

  constructor(isRequired: Boolean, formatter: ArgumentFormatter<Byte>)
    : super(Property.empty(), isRequired, false, formatter)

  constructor(isRequired: Boolean)
    : super(Property.empty(), isRequired, false, ArgumentFormatter(Byte::toString))
}
