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

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit = {}): ULongFlag =
  ULongFlagImpl(FlagOptions(ULong::class).also(action))

fun nullableULongFlag(action: NullableFlagOptions<ULong>.() -> Unit = {}): Flag<Argument<ULong?>, ULong?> =
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
{
  constructor(opts: FlagOptions<ULong>) : this(
    longForm   = FlagOptions<ULong>::longForm.property(opts),
    shortForm  = FlagOptions<ULong>::shortForm.property(opts),
    isRequired = FlagOptions<ULong>::required.property(opts),
    filter     = FlagOptions<ULong>::flagFilter.property(opts),
    argument   = ULongArgumentImpl(opts.argument),
  )
}
