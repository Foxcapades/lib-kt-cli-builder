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

fun longFlag(action: FlagOptions<Long>.() -> Unit = {}): LongFlag =
  LongFlagImpl(FlagOptions(Long::class).also(action))

fun nullableLongFlag(action: NullableFlagOptions<Long>.() -> Unit = {}): Flag<Argument<Long?>, Long?> =
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
{
  constructor(opts: FlagOptions<Long>) : this(
    longForm   = FlagOptions<Long>::longForm.property(opts),
    shortForm  = FlagOptions<Long>::shortForm.property(opts),
    isRequired = FlagOptions<Long>::required.property(opts),
    filter     = FlagOptions<Long>::flagFilter.property(opts),
    argument   = LongArgumentImpl(opts.argument),
  )
}
