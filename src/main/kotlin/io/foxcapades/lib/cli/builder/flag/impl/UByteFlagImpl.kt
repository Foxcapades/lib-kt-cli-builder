package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.UByteArgument
import io.foxcapades.lib.cli.builder.arg.impl.UByteArgumentImpl
import io.foxcapades.lib.cli.builder.flag.UByteFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property

internal class UByteFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<UByteFlag, UByteArgument, UByte>>,
  argument: UByteArgument
)
  : AbstractFlagImpl<UByteFlag, UByteArgument, UByte>(longForm, shortForm, isRequired, filter, argument)
  , UByteFlag
{
  constructor(flag: FlagOptions<UByte>) : this(
    longForm   = FlagOptions<UByte>::longForm.property(flag),
    shortForm  = FlagOptions<UByte>::shortForm.property(flag),
    isRequired = FlagOptions<UByte>::required.property(flag),
    filter     = FlagOptions<UByte>::flagFilter.property(flag),
    argument   = UByteArgumentImpl(flag.argument),
  )
}
