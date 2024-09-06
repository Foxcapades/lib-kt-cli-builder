package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.arg.LongArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
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
    isRequired = FlagOptions<Long>::required.property(flag),
    filter     = FlagOptions<Long>::flagFilter.property(flag),
    argument   = LongArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableLongFlag(action: NullableFlagOptions<Long>.() -> Unit): Flag<Argument<Long?>, Long?> =
  GeneralFlagImpl.of(NullableFlagOptions(Long::class).also(action))

internal class LongFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<LongFlag, LongArgument, Long>>,
  argument:   LongArgument
)
  : AbstractFlagImpl<LongFlag, LongArgument, Long>(longForm, shortForm, isRequired, filter, argument)
  , LongFlag
