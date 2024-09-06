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

fun intFlag(action: FlagOptions<Int>.() -> Unit = {}): IntFlag =
  IntFlagImpl(FlagOptions(Int::class).also(action))

fun nullableIntFlag(action: NullableFlagOptions<Int>.() -> Unit = {}): Flag<Argument<Int?>, Int?> =
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
{
  constructor(opts: FlagOptions<Int>) : this(
    longForm   = FlagOptions<Int>::longForm.property(opts),
    shortForm  = FlagOptions<Int>::shortForm.property(opts),
    isRequired = FlagOptions<Int>::required.property(opts),
    filter     = FlagOptions<Int>::flagFilter.property(opts),
    argument   = IntArgumentImpl(opts.argument),
  )
}
