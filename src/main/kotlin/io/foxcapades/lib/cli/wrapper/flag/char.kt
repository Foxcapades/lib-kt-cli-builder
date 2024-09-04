package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.CharArgument
import io.foxcapades.lib.cli.wrapper.arg.CharArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface CharFlag : ScalarFlag<CharArgument, Char>

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(longForm: String, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun charFlag(shortForm: Char, noinline action: FlagOptions<Char>.() -> Unit = {}) =
  charFlag { this.shortForm = shortForm; action() }

fun charFlag(action: FlagOptions<Char>.() -> Unit): CharFlag {
  val flag = FlagOptions(Char::class).also(action)

  return CharFlagImpl(
    longForm   = FlagOptions<Char>::longForm.property(flag),
    shortForm  = FlagOptions<Char>::shortForm.property(flag),
    isRequired = FlagOptions<Char>::requireFlag.property(flag),
    argument   = CharArgumentImpl(
      default     = FlagOptions<Char>::default.property(flag),
      isRequired  = FlagOptions<Char>::requireArg.property(flag),
      shouldQuote = FlagOptions<Char>::shouldQuote.property(flag),
      formatter   = FlagOptions<Char>::formatter.property(flag),
    )
  )
}

fun nullableCharFlag(action: NullableFlagOptions<Char>.() -> Unit): Flag<Argument<Char?>, Char?> {
  val flag = NullableFlagOptions(Char::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Char>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Char>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Char>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Char::class,
      true,
      default     = NullableFlagOptions<Char>::default.property(flag),
      shouldQuote = NullableFlagOptions<Char>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Char>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Char>::formatter.property(flag),
    )
  )
}


internal class CharFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  argument: CharArgument
)
  : AbstractFlagImpl<CharArgument, Char>(longForm, shortForm, isRequired, argument)
  , CharFlag
