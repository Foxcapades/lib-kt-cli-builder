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

fun floatFlag(action: FlagOptions<Float>.() -> Unit = {}): FloatFlag =
  FloatFlagImpl(FlagOptions(Float::class).also(action))

fun nullableFloatFlag(action: NullableFlagOptions<Float>.() -> Unit = {}): Flag<Argument<Float?>, Float?> =
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
{
  constructor(opts: FlagOptions<Float>) : this(
    longForm   = FlagOptions<Float>::longForm.property(opts),
    shortForm  = FlagOptions<Float>::shortForm.property(opts),
    isRequired = FlagOptions<Float>::required.property(opts),
    filter     = FlagOptions<Float>::flagFilter.property(opts),
    argument   = FloatArgumentImpl(opts.argument),
  )
}
