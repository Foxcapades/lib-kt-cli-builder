package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.arg.UByteArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UByteFlag : ScalarFlag<UByteArgument, UByte>

@Suppress("NOTHING_TO_INLINE")
inline fun ubyteFlag(longForm: String, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ubyteFlag(shortForm: Char, noinline action: FlagOptions<UByte>.() -> Unit = {}) =
  ubyteFlag { this.shortForm = shortForm; action() }

fun ubyteFlag(action: FlagOptions<UByte>.() -> Unit): UByteFlag {
  val flag = FlagOptions(UByte::class).also(action)

  return UByteFlagImpl(
    longForm   = FlagOptions<UByte>::longForm.property(flag),
    shortForm  = FlagOptions<UByte>::shortForm.property(flag),
    isRequired = FlagOptions<UByte>::required.property(flag),
    filter     = FlagOptions<UByte>::flagFilter.property(flag),
    argument   = UByteArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableUByteFlag(action: NullableFlagOptions<UByte>.() -> Unit): Flag<Argument<UByte?>, UByte?> =
  GeneralFlagImpl.of(NullableFlagOptions(UByte::class).also(action))

internal class UByteFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<UByteFlag, UByteArgument, UByte>>,
  argument:   UByteArgument
)
  : AbstractFlagImpl<UByteFlag, UByteArgument, UByte>(longForm, shortForm, isRequired, filter, argument)
  , UByteFlag
