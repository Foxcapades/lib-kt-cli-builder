package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.arg.UIntArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UIntFlag : ScalarFlag<UIntArgument, UInt>

@Suppress("NOTHING_TO_INLINE")
inline fun uintFlag(longForm: String, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun uintFlag(shortForm: Char, noinline action: FlagOptions<UInt>.() -> Unit = {}) =
  uintFlag { this.shortForm = shortForm; action() }

fun uintFlag(action: FlagOptions<UInt>.() -> Unit): UIntFlag {
  val flag = FlagOptions(UInt::class).also(action)

  return UIntFlagImpl(
    longForm   = FlagOptions<UInt>::longForm.property(flag),
    shortForm  = FlagOptions<UInt>::shortForm.property(flag),
    isRequired = FlagOptions<UInt>::required.property(flag),
    filter     = FlagOptions<UInt>::flagFilter.property(flag),
    argument   = UIntArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableUIntFlag(action: NullableFlagOptions<UInt>.() -> Unit): Flag<Argument<UInt?>, UInt?> =
  GeneralFlagImpl.of(NullableFlagOptions(UInt::class).also(action))

internal class UIntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<UIntFlag, UIntArgument, UInt>>,
  argument:   UIntArgument
)
  : AbstractFlagImpl<UIntFlag, UIntArgument, UInt>(longForm, shortForm, isRequired, filter, argument)
  , UIntFlag
