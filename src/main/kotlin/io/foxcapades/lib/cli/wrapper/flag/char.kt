package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.CharArgument
import io.foxcapades.lib.cli.wrapper.arg.CharArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface CharFlag : ScalarFlag<CharArgument, Char>

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(longForm: String, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(shortForm: Char, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.shortForm = shortForm; action() }

fun charFlag(action: FlagOptions<Char>.() -> Unit = {}): CharFlag =
  CharFlagImpl(FlagOptions(Char::class).also(action))

fun nullableCharFlag(action: NullableFlagOptions<Char>.() -> Unit = {}): Flag<Argument<Char?>, Char?> =
  GeneralFlagImpl.of(NullableFlagOptions(Char::class).also(action))

internal class CharFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<CharFlag, CharArgument, Char>>,
  argument:   CharArgument
)
  : AbstractFlagImpl<CharFlag, CharArgument, Char>(longForm, shortForm, isRequired, filter, argument)
  , CharFlag
{
  constructor(opts: FlagOptions<Char>) : this(
    longForm   = FlagOptions<Char>::longForm.property(opts),
    shortForm  = FlagOptions<Char>::shortForm.property(opts),
    isRequired = FlagOptions<Char>::required.property(opts),
    filter     = FlagOptions<Char>::flagFilter.property(opts),
    argument   = CharArgumentImpl(opts.argument)
  )
}
