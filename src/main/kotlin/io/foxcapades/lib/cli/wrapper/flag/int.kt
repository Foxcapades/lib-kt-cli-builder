package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.IntArgument
import io.foxcapades.lib.cli.wrapper.arg.IntArgumentImpl
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
    isRequired = FlagOptions<Int>::requireFlag.property(flag),
    argument   = IntArgumentImpl(
      default     = FlagOptions<Int>::default.property(flag),
      isRequired  = FlagOptions<Int>::requireArg.property(flag),
      shouldQuote = FlagOptions<Int>::shouldQuote.property(flag),
      formatter   = FlagOptions<Int>::formatter.property(flag),
    )
  )
}

fun nullableIntFlag(action: NullableFlagOptions<Int>.() -> Unit): Flag<Argument<Int?>, Int?> {
  val flag = NullableFlagOptions(Int::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Int>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Int>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Int>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Int::class,
      true,
      default     = NullableFlagOptions<Int>::default.property(flag),
      shouldQuote = NullableFlagOptions<Int>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Int>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Int>::formatter.property(flag),
    )
  )
}

internal class IntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   IntArgument
)
  : AbstractFlagImpl<IntArgument, Int>(longForm, shortForm, isRequired, argument)
  , IntFlag
