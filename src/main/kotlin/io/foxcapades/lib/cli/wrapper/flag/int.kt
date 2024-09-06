package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.IntArgument
import io.foxcapades.lib.cli.wrapper.arg.IntArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface IntFlag : ScalarFlag<IntArgument, Int>

@Suppress("NOTHING_TO_INLINE")
inline fun intFlag(longForm: String, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun intFlag(shortForm: Char, noinline action: FlagOptions<Int>.() -> Unit = {}) =
  intFlag { this.shortForm = shortForm; action() }

fun intFlag(action: FlagOptions<Int>.() -> Unit): IntFlag {
  val flag = FlagOptions(Int::class).also(action)

  return IntFlagImpl(
    longForm   = FlagOptions<Int>::longForm.property(flag),
    shortForm  = FlagOptions<Int>::shortForm.property(flag),
    isRequired = FlagOptions<Int>::required.property(flag),
    filter     = FlagOptions<Int>::flagFilter.property(flag),
    argument   = IntArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableIntFlag(action: NullableFlagOptions<Int>.() -> Unit): Flag<Argument<Int?>, Int?> =
  GeneralFlagImpl.of(NullableFlagOptions(Int::class).also(action))

internal class IntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<IntFlag, IntArgument, Int>>,
  argument:   IntArgument
)
  : AbstractFlagImpl<IntFlag, IntArgument, Int>(longForm, shortForm, isRequired, filter, argument)
  , IntFlag
