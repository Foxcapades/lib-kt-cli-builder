package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
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

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit = {}): UShortFlag =
  UShortFlagImpl(FlagOptions(UShort::class).also(action))

fun nullableUShortFlag(action: NullableFlagOptions<UShort>.() -> Unit = {}): Flag<Argument<UShort?>, UShort?> =
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
{
  constructor(opts: FlagOptions<UShort>) : this(
    longForm   = FlagOptions<UShort>::longForm.property(opts),
    shortForm  = FlagOptions<UShort>::shortForm.property(opts),
    isRequired = FlagOptions<UShort>::required.property(opts),
    filter     = FlagOptions<UShort>::flagFilter.property(opts),
    argument   = UShortArgumentImpl(opts.argument),
  )
}
