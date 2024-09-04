package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.arg.ShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface ShortFlag : ScalarFlag<ShortArgument, Short>

fun shortFlag(action: FlagOptions<Short>.() -> Unit): ShortFlag {
  val flag = FlagOptions(Short::class).also(action)

  return ShortFlagImpl(
    longForm   = FlagOptions<Short>::longForm.property(flag),
    shortForm  = FlagOptions<Short>::shortForm.property(flag),
    isRequired = FlagOptions<Short>::requireFlag.property(flag),
    argument   = ShortArgumentImpl(
      default     = FlagOptions<Short>::default.property(flag),
      isRequired  = FlagOptions<Short>::requireArg.property(flag),
      shouldQuote = FlagOptions<Short>::shouldQuote.property(flag),
      formatter   = FlagOptions<Short>::formatter.property(flag),
    )
  )
}

fun nullableShortFlag(action: NullableFlagOptions<Short>.() -> Unit): Flag<Argument<Short?>, Short?> {
  val flag = NullableFlagOptions(Short::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<Short>::longForm.property(flag),
    shortForm  = NullableFlagOptions<Short>::shortForm.property(flag),
    isRequired = NullableFlagOptions<Short>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      Short::class,
      true,
      default     = NullableFlagOptions<Short>::default.property(flag),
      shouldQuote = NullableFlagOptions<Short>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<Short>::requireArg.property(flag),
      formatter   = NullableFlagOptions<Short>::formatter.property(flag),
    )
  )
}

internal class ShortFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   ShortArgument
)
  : AbstractFlagImpl<ShortArgument, Short>(longForm, shortForm, isRequired, argument)
  , ShortFlag
