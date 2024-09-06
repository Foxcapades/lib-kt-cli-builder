package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.UShortArgument
import io.foxcapades.lib.cli.wrapper.arg.UShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UShortFlag : ScalarFlag<UShortArgument, UShort>

@Suppress("NOTHING_TO_INLINE")
inline fun ushortFlag(longForm: String, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ushortFlag(shortForm: Char, noinline action: FlagOptions<UShort>.() -> Unit = {}) =
  ushortFlag { this.shortForm = shortForm; action() }

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit): UShortFlag {
  val flag = FlagOptions(UShort::class).also(action)

  return UShortFlagImpl(
    longForm   = FlagOptions<UShort>::longForm.property(flag),
    shortForm  = FlagOptions<UShort>::shortForm.property(flag),
    isRequired = FlagOptions<UShort>::required.property(flag),
    filter     = FlagOptions<UShort>::flagFilter.property(flag),
    argument   = UShortArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableUShortFlag(action: NullableFlagOptions<UShort>.() -> Unit): Flag<Argument<UShort?>, UShort?> =
  GeneralFlagImpl.of(NullableFlagOptions(UShort::class).also(action))

internal class UShortFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<UShortFlag, UShortArgument, UShort>>,
  argument:   UShortArgument
)
  : AbstractFlagImpl<UShortFlag, UShortArgument, UShort>(longForm, shortForm, isRequired, filter, argument)
  , UShortFlag
