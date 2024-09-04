package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.arg.ByteArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface ByteFlag : ScalarFlag<ByteArgument, Byte>

@Suppress("NOTHING_TO_INLINE")
inline fun byteFlag(longForm: String, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun byteFlag(shortForm: Char, noinline action: FlagOptions<Byte>.() -> Unit = {}) =
  byteFlag { this.shortForm = shortForm; action() }

fun byteFlag(action: FlagOptions<Byte>.() -> Unit): ByteFlag {
  val flag = FlagOptions(Byte::class).also(action)

  return ByteFlagImpl(
    longForm   = FlagOptions<Byte>::longForm.property(flag),
    shortForm  = FlagOptions<Byte>::shortForm.property(flag),
    isRequired = FlagOptions<Byte>::requireFlag.property(flag),
    argument   = ByteArgumentImpl(
      default     = FlagOptions<Byte>::default.property(flag),
      isRequired  = FlagOptions<Byte>::requireArg.property(flag),
      shouldQuote = FlagOptions<Byte>::shouldQuote.property(flag),
      formatter   = FlagOptions<Byte>::formatter.property(flag),
    )
  )
}

fun nullableByteFlag(action: NullableFlagOptions<Byte>.() -> Unit): Flag<Argument<Byte?>, Byte?> {
  val flag = NullableFlagOptions(Byte::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Byte>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Byte>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Byte>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Byte::class,
      true,
      default     = NullableFlagOptions<Byte>::default.property(flag),
      shouldQuote = NullableFlagOptions<Byte>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Byte>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Byte>::formatter.property(flag),
    )
  )
}

internal class ByteFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   ByteArgument
)
  : AbstractFlagImpl<ByteArgument, Byte>(longForm, shortForm, isRequired, argument)
  , ByteFlag
