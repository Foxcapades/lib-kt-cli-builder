package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface DoubleFlag : ScalarFlag<DoubleArgument, Double>

@Suppress("NOTHING_TO_INLINE")
inline fun doubleFlag(longForm: String, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun doubleFlag(shortForm: Char, noinline action: FlagOptions<Double>.() -> Unit = {}) =
  doubleFlag { this.shortForm = shortForm; action() }

fun doubleFlag(action: FlagOptions<Double>.() -> Unit = {}): DoubleFlag =
  DoubleFlagImpl(FlagOptions(Double::class).also(action))

fun nullableDoubleFlag(action: NullableFlagOptions<Double>.() -> Unit = {}): Flag<Argument<Double?>, Double?> =
  GeneralFlagImpl.of(NullableFlagOptions(Double::class).also(action))

internal class DoubleFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<DoubleFlag, DoubleArgument, Double>>,
  argument:   DoubleArgument
)
  : AbstractFlagImpl<DoubleFlag, DoubleArgument, Double>(longForm, shortForm, isRequired, filter, argument)
  , DoubleFlag
{
  constructor(opts: FlagOptions<Double>) : this(
    longForm   = FlagOptions<Double>::longForm.property(opts),
    shortForm  = FlagOptions<Double>::shortForm.property(opts),
    isRequired = FlagOptions<Double>::required.property(opts),
    filter     = FlagOptions<Double>::flagFilter.property(opts),
    argument   = DoubleArgumentImpl(opts.argument),
  )
}
