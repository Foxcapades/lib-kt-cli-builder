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

fun stringFlag(action: FlagOptions<String>.() -> Unit): StringFlag {
  val flag = FlagOptions(String::class).also(action)

  return StringFlagImpl(
    longForm   = FlagOptions<String>::longForm.property(flag),
    shortForm  = FlagOptions<String>::shortForm.property(flag),
    isRequired = FlagOptions<String>::required.property(flag),
    filter     = FlagOptions<String>::flagFilter.property(flag),
    argument   = StringArgumentImpl(
      default     = ArgOptions<Boolean>::default.property(flag.argument),
      isRequired  = ArgOptions<Boolean>::required.property(flag.argument),
      shouldQuote = ArgOptions<Boolean>::shouldQuote.property(flag.argument),
      formatter   = ArgOptions<Boolean>::formatter.property(flag.argument),
      filter      = ArgOptions<Boolean>::filter.property(flag.argument),
    )
  )
}

fun nullableStringFlag(action: NullableFlagOptions<String>.() -> Unit): Flag<Argument<String?>, String?> =
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
