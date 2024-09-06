package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.arg.ByteArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
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
    isRequired = FlagOptions<Byte>::required.property(flag),
    filter     = FlagOptions<Byte>::flagFilter.property(flag),
    argument   = ByteArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableByteFlag(action: NullableFlagOptions<Byte>.() -> Unit): Flag<Argument<Byte?>, Byte?> =
  GeneralFlagImpl.of(NullableFlagOptions(Byte::class).also(action))

internal class ByteFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ByteFlag, ByteArgument, Byte>>,
  argument:   ByteArgument
)
  : AbstractFlagImpl<ByteFlag, ByteArgument, Byte>(longForm, shortForm, isRequired, filter, argument)
  , ByteFlag
