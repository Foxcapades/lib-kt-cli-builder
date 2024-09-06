package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.arg.ShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface ShortFlag : ScalarFlag<ShortArgument, Short>

@Suppress("NOTHING_TO_INLINE")
inline fun shortFlag(longForm: String, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun shortFlag(shortForm: Char, noinline action: FlagOptions<Short>.() -> Unit = {}) =
  shortFlag { this.shortForm = shortForm; action() }

fun shortFlag(action: FlagOptions<Short>.() -> Unit = {}): ShortFlag =
  ShortFlagImpl(FlagOptions(Short::class).also(action))

fun nullableShortFlag(action: NullableFlagOptions<Short>.() -> Unit = {}): Flag<Argument<Short?>, Short?> =
  GeneralFlagImpl.of(NullableFlagOptions(Short::class).also(action))

internal class ShortFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ShortFlag, ShortArgument, Short>>,
  argument:   ShortArgument
)
  : AbstractFlagImpl<ShortFlag, ShortArgument, Short>(longForm, shortForm, isRequired, filter, argument)
  , ShortFlag
{
  constructor(opts: FlagOptions<Short>) : this(
    longForm   = FlagOptions<Short>::longForm.property(opts),
    shortForm  = FlagOptions<Short>::shortForm.property(opts),
    isRequired = FlagOptions<Short>::required.property(opts),
    filter     = FlagOptions<Short>::flagFilter.property(opts),
    argument   = ShortArgumentImpl(opts.argument),
  )
}
