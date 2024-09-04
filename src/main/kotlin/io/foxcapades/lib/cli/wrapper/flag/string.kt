package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.arg.StringArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import io.foxcapades.lib.cli.wrapper.util.setIfAbsent

interface StringFlag : ScalarFlag<StringArgument, String>

fun stringFlag(action: FlagOptions<String>.() -> Unit): StringFlag {
  val flag = FlagOptions(String::class).also(action)

  return StringFlagImpl(
    longForm   = FlagOptions<String>::longForm.property(flag),
    shortForm  = FlagOptions<String>::shortForm.property(flag),
    isRequired = FlagOptions<String>::requireFlag.property(flag),
    argument   = StringArgumentImpl(
      default     = FlagOptions<String>::default.property(flag),
      isRequired  = FlagOptions<String>::requireArg.property(flag),
      shouldQuote = FlagOptions<String>::shouldQuote.property<Boolean>(flag).setIfAbsent(true),
      formatter   = FlagOptions<String>::formatter.property(flag),
    )
  )
}

fun nullableStringFlag(action: NullableFlagOptions<String>.() -> Unit): Flag<Argument<String?>, String?> {
  val flag = NullableFlagOptions(String::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<String>::longForm.property(flag),
    shortForm  = NullableFlagOptions<String>::shortForm.property(flag),
    isRequired = NullableFlagOptions<String>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      String::class,
      true,
      default     = NullableFlagOptions<String>::default.property(flag),
      shouldQuote = NullableFlagOptions<String>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<String>::requireArg.property(flag),
      formatter   = NullableFlagOptions<String>::formatter.property(flag),
    )
  )
}

internal class StringFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   StringArgument
)
  : AbstractFlagImpl<StringArgument, String>(longForm, shortForm, isRequired, argument)
  , StringFlag
