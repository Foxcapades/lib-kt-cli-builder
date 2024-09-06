package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.arg.ULongArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface ULongFlag : ScalarFlag<ULongArgument, ULong>

@Suppress("NOTHING_TO_INLINE")
inline fun ulongFlag(longForm: String, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun ulongFlag(shortForm: Char, noinline action: FlagOptions<ULong>.() -> Unit = {}) =
  ulongFlag { this.shortForm = shortForm; action() }

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit): ULongFlag {
  val flag = FlagOptions(ULong::class).also(action)

  return ULongFlagImpl(
    longForm   = FlagOptions<ULong>::longForm.property(flag),
    shortForm  = FlagOptions<ULong>::shortForm.property(flag),
    isRequired = FlagOptions<ULong>::required.property(flag),
    filter     = FlagOptions<ULong>::flagFilter.property(flag),
    argument   = ULongArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableULongFlag(action: NullableFlagOptions<ULong>.() -> Unit): Flag<Argument<ULong?>, ULong?> =
  GeneralFlagImpl.of(NullableFlagOptions(ULong::class).also(action))

internal class ULongFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ULongFlag, ULongArgument, ULong>>,
  argument:   ULongArgument
)
  : AbstractFlagImpl<ULongFlag, ULongArgument, ULong>(longForm, shortForm, isRequired, filter, argument)
  , ULongFlag
