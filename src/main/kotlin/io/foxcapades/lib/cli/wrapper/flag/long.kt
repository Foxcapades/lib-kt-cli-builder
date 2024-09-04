package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.arg.LongArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface LongFlag : ScalarFlag<LongArgument, Long>

@Suppress("NOTHING_TO_INLINE")
inline fun longFlag(longForm: String, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun longFlag(shortForm: Char, noinline action: FlagOptions<Long>.() -> Unit = {}) =
  longFlag { this.shortForm = shortForm; action() }


fun longFlag(action: FlagOptions<Long>.() -> Unit): LongFlag {
  val flag = FlagOptions(Long::class).also(action)

  return LongFlagImpl(
    longForm   = FlagOptions<Long>::longForm.property(flag),
    shortForm  = FlagOptions<Long>::shortForm.property(flag),
    isRequired = FlagOptions<Long>::requireFlag.property(flag),
    argument   = LongArgumentImpl(
      default     = FlagOptions<Long>::default.property(flag),
      isRequired  = FlagOptions<Long>::requireArg.property(flag),
      shouldQuote = FlagOptions<Long>::shouldQuote.property(flag),
      formatter   = FlagOptions<Long>::formatter.property(flag),
    )
  )
}

fun nullableLongFlag(action: NullableFlagOptions<Long>.() -> Unit): Flag<Argument<Long?>, Long?> {
  val flag = NullableFlagOptions(Long::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Long>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Long>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Long>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Long::class,
      true,
      default     = NullableFlagOptions<Long>::default.property(flag),
      shouldQuote = NullableFlagOptions<Long>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Long>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Long>::formatter.property(flag),
    )
  )
}

internal class LongFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   LongArgument
)
  : AbstractFlagImpl<LongArgument, Long>(longForm, shortForm, isRequired, argument)
  , LongFlag
