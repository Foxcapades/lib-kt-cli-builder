package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.FloatArgument
import io.foxcapades.lib.cli.wrapper.arg.FloatArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface FloatFlag : ScalarFlag<FloatArgument, Float>

@Suppress("NOTHING_TO_INLINE")
inline fun floatFlag(longForm: String, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun floatFlag(shortForm: Char, noinline action: FlagOptions<Float>.() -> Unit = {}) =
  floatFlag { this.shortForm = shortForm; action() }

fun floatFlag(action: FlagOptions<Float>.() -> Unit): FloatFlag {
  val flag = FlagOptions(Float::class).also(action)

  return FloatFlagImpl(
    longForm   = FlagOptions<Float>::longForm.property(flag),
    shortForm  = FlagOptions<Float>::shortForm.property(flag),
    isRequired = FlagOptions<Float>::required.property(flag),
    filter     = FlagOptions<Float>::flagFilter.property(flag),
    argument   = FloatArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableFloatFlag(action: NullableFlagOptions<Float>.() -> Unit): Flag<Argument<Float?>, Float?> =
  GeneralFlagImpl.of(NullableFlagOptions(Float::class).also(action))

internal class FloatFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<FloatFlag, FloatArgument, Float>>,
  argument:   FloatArgument
)
  : AbstractFlagImpl<FloatFlag, FloatArgument, Float>(longForm, shortForm, isRequired, filter, argument)
  , FloatFlag
