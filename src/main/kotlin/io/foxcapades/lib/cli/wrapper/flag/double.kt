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

fun doubleFlag(action: FlagOptions<Double>.() -> Unit): DoubleFlag {
  val flag = FlagOptions(Double::class).also(action)

  return DoubleFlagImpl(
    longForm   = FlagOptions<Double>::longForm.property(flag),
    shortForm  = FlagOptions<Double>::shortForm.property(flag),
    isRequired = FlagOptions<Double>::required.property(flag),
    filter     = FlagOptions<Double>::flagFilter.property(flag),
    argument   = DoubleArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableDoubleFlag(action: NullableFlagOptions<Double>.() -> Unit): Flag<Argument<Double?>, Double?> =
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
