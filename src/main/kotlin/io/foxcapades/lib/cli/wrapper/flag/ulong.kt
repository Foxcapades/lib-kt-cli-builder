package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.arg.ULongArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface ULongFlag : ScalarFlag<ULongArgument, ULong>

fun ulongFlag(action: FlagOptions<ULong>.() -> Unit): ULongFlag {
  val flag = FlagOptions(ULong::class).also(action)

  return ULongFlagImpl(
    longForm   = FlagOptions<ULong>::longForm.property(flag),
    shortForm  = FlagOptions<ULong>::shortForm.property(flag),
    isRequired = FlagOptions<ULong>::requireFlag.property(flag),
    argument   = ULongArgumentImpl(
      default     = FlagOptions<ULong>::default.property(flag),
      isRequired  = FlagOptions<ULong>::requireArg.property(flag),
      shouldQuote = FlagOptions<ULong>::shouldQuote.property(flag),
      formatter   = FlagOptions<ULong>::formatter.property(flag),
    )
  )
}

fun nullableULongFlag(action: NullableFlagOptions<ULong>.() -> Unit): Flag<Argument<ULong?>, ULong?> {
  val flag = NullableFlagOptions(ULong::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<ULong>::longForm.property(flag),
    shortForm  = NullableFlagOptions<ULong>::shortForm.property(flag),
    isRequired = NullableFlagOptions<ULong>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      ULong::class,
      true,
      default     = NullableFlagOptions<ULong>::default.property(flag),
      shouldQuote = NullableFlagOptions<ULong>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<ULong>::requireArg.property(flag),
      formatter   = NullableFlagOptions<ULong>::formatter.property(flag),
    )
  )
}

internal class ULongFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   ULongArgument
)
  : AbstractFlagImpl<ULongArgument, ULong>(longForm, shortForm, isRequired, argument)
  , ULongFlag
