package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.*
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
    isRequired = FlagOptions<Float>::requireFlag.property(flag),
    argument   = FloatArgumentImpl(
      default     = FlagOptions<Float>::default.property(flag),
      isRequired  = FlagOptions<Float>::requireArg.property(flag),
      shouldQuote = FlagOptions<Float>::shouldQuote.property(flag),
      formatter   = FlagOptions<Float>::formatter.property(flag),
    )
  )
}

fun nullableFloatFlag(action: NullableFlagOptions<Float>.() -> Unit): Flag<Argument<Float?>, Float?> {
  val flag = NullableFlagOptions(Float::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Float>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Float>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Float>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Float::class,
      true,
      default     = NullableFlagOptions<Float>::default.property(flag),
      shouldQuote = NullableFlagOptions<Float>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Float>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Float>::formatter.property(flag),
    )
  )
}

internal class FloatFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  argument: FloatArgument
)
  : AbstractFlagImpl<FloatArgument, Float>(longForm, shortForm, isRequired, argument)
  , FloatFlag
