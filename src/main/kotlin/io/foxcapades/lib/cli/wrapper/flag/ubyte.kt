package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.arg.UByteArgumentImpl
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property

interface UByteFlag : ScalarFlag<UByteArgument, UByte>

fun ubyteFlag(action: FlagOptions<UByte>.() -> Unit): UByteFlag {
  val flag = FlagOptions(UByte::class).also(action)

  return UByteFlagImpl(
    longForm   = FlagOptions<UByte>::longForm.property(flag),
    shortForm  = FlagOptions<UByte>::shortForm.property(flag),
    isRequired = FlagOptions<UByte>::requireFlag.property(flag),
    argument   = UByteArgumentImpl(
      default     = FlagOptions<UByte>::default.property(flag),
      isRequired  = FlagOptions<UByte>::requireArg.property(flag),
      shouldQuote = FlagOptions<UByte>::shouldQuote.property(flag),
      formatter   = FlagOptions<UByte>::formatter.property(flag),
    )
  )
}

fun nullableUByteFlag(action: NullableFlagOptions<UByte>.() -> Unit): Flag<Argument<UByte?>, UByte?> {
  val flag = NullableFlagOptions(UByte::class).also(action)

  return GeneralFlagImpl(
    longForm   = NullableFlagOptions<UByte>::longForm.property(flag),
    shortForm  = NullableFlagOptions<UByte>::shortForm.property(flag),
    isRequired = NullableFlagOptions<UByte>::requireFlag.property(flag),
    argument   = GeneralArgumentImpl(
      UByte::class,
      true,
      default     = NullableFlagOptions<UByte>::default.property(flag),
      shouldQuote = NullableFlagOptions<UByte>::shouldQuote.property(flag),
      isRequired  = NullableFlagOptions<UByte>::requireArg.property(flag),
      formatter   = NullableFlagOptions<UByte>::formatter.property(flag),
    )
  )
}
internal class UByteFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  argument:   UByteArgument
)
  : AbstractFlagImpl<UByteArgument, UByte>(longForm, shortForm, isRequired, argument)
  , UByteFlag
