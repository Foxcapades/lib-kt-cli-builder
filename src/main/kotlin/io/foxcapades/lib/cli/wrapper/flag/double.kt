package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
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
    isRequired = FlagOptions<Double>::requireFlag.property(flag),
    argument   = DoubleArgumentImpl(
      default     = FlagOptions<Double>::default.property(flag),
      isRequired  = FlagOptions<Double>::requireArg.property(flag),
      shouldQuote = FlagOptions<Double>::shouldQuote.property(flag),
      formatter   = FlagOptions<Double>::formatter.property(flag),
    )
  )
}

fun nullableDoubleFlag(action: NullableFlagOptions<Double>.() -> Unit): Flag<Argument<Double?>, Double?> {
  val flag = NullableFlagOptions(Double::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Double>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Double>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Double>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Double::class,
      true,
      default     = NullableFlagOptions<Double>::default.property(flag),
      shouldQuote = NullableFlagOptions<Double>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Double>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Double>::formatter.property(flag),
    )
  )
}

internal class DoubleFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  argument: DoubleArgument
)
  : AbstractFlagImpl<DoubleArgument, Double>(longForm, shortForm, isRequired, argument)
  , DoubleFlag
