package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.arg.UIntArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UIntFlag : ScalarFlag<UIntArgument, UInt>

fun uintFlag(action: FlagOptions<UInt>.() -> Unit): UIntFlag {
  val flag = FlagOptions(UInt::class).also(action)

  return UIntFlagImpl(
    longForm   = FlagOptions<UInt>::longForm.property(flag),
    shortForm  = FlagOptions<UInt>::shortForm.property(flag),
    isRequired = FlagOptions<UInt>::requireFlag.property(flag),
    argument   = UIntArgumentImpl(
      default     = FlagOptions<UInt>::default.property(flag),
      isRequired  = FlagOptions<UInt>::requireArg.property(flag),
      shouldQuote = FlagOptions<UInt>::shouldQuote.property(flag),
      formatter   = FlagOptions<UInt>::formatter.property(flag),
    )
  )
}

fun nullableUIntFlag(action: NullableFlagOptions<UInt>.() -> Unit): Flag<Argument<UInt?>, UInt?> {
  val flag = NullableFlagOptions(UInt::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<UInt>::longForm.property(flag),
    shortForm  = NullableFlagOptions<UInt>::shortForm.property(flag),
    isRequired = NullableFlagOptions<UInt>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      UInt::class,
      true,
      default     = NullableFlagOptions<UInt>::default.property(flag),
      shouldQuote = NullableFlagOptions<UInt>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<UInt>::requireArg.property(flag),
      formatter   = NullableFlagOptions<UInt>::formatter.property(flag),
    )
  )
}
internal class UIntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   UIntArgument
)
  : AbstractFlagImpl<UIntArgument, UInt>(longForm, shortForm, isRequired, argument)
  , UIntFlag
