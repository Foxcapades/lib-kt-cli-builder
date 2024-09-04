package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.UShortArgument
import io.foxcapades.lib.cli.wrapper.arg.UShortArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UShortFlag : ScalarFlag<UShortArgument, UShort>

fun ushortFlag(action: FlagOptions<UShort>.() -> Unit): UShortFlag {
  val flag = FlagOptions(UShort::class).also(action)

  return UShortFlagImpl(
    longForm   = FlagOptions<UShort>::longForm.property(flag),
    shortForm  = FlagOptions<UShort>::shortForm.property(flag),
    isRequired = FlagOptions<UShort>::requireFlag.property(flag),
    argument   = UShortArgumentImpl(
      default     = FlagOptions<UShort>::default.property(flag),
      isRequired  = FlagOptions<UShort>::requireArg.property(flag),
      shouldQuote = FlagOptions<UShort>::shouldQuote.property(flag),
      formatter   = FlagOptions<UShort>::formatter.property(flag),
    )
  )
}

fun nullableUShortFlag(action: NullableFlagOptions<UShort>.() -> Unit): Flag<Argument<UShort?>, UShort?> {
  val flag = NullableFlagOptions(UShort::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<UShort>::longForm.property(flag),
    shortForm  = NullableFlagOptions<UShort>::shortForm.property(flag),
    isRequired = NullableFlagOptions<UShort>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      UShort::class,
      true,
      default     = NullableFlagOptions<UShort>::default.property(flag),
      shouldQuote = NullableFlagOptions<UShort>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<UShort>::requireArg.property(flag),
      formatter   = NullableFlagOptions<UShort>::formatter.property(flag),
    )
  )
}

internal class UShortFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   UShortArgument
)
  : AbstractFlagImpl<UShortArgument, UShort>(longForm, shortForm, isRequired, argument)
  , UShortFlag
