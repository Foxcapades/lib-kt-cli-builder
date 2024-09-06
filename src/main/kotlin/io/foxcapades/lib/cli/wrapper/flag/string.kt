package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.arg.StringArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface StringFlag : ScalarFlag<StringArgument, String>

@Suppress("NOTHING_TO_INLINE")
inline fun stringFlag(longForm: String, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun stringFlag(shortForm: Char, noinline action: FlagOptions<String>.() -> Unit = {}) =
  stringFlag { this.shortForm = shortForm; action() }

fun stringFlag(action: FlagOptions<String>.() -> Unit = {}): StringFlag =
  StringFlagImpl(FlagOptions(String::class).also(action))

fun nullableStringFlag(action: NullableFlagOptions<String>.() -> Unit = {}): Flag<Argument<String?>, String?> =
  GeneralFlagImpl.of(NullableFlagOptions(String::class).also(action))

internal class StringFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<StringFlag, StringArgument, String>>,
  argument:   StringArgument
)
  : AbstractFlagImpl<StringFlag, StringArgument, String>(longForm, shortForm, isRequired, filter, argument)
  , StringFlag
{
  constructor(opts: FlagOptions<String>) : this(
    longForm   = FlagOptions<String>::longForm.property(opts),
    shortForm  = FlagOptions<String>::shortForm.property(opts),
    isRequired = FlagOptions<String>::required.property(opts),
    filter     = FlagOptions<String>::flagFilter.property(opts),
    argument   = StringArgumentImpl(opts.argument),
  )
}
