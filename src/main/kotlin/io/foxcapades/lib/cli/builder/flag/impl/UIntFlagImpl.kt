package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.UIntArgument
import io.foxcapades.lib.cli.builder.arg.impl.UIntArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.UIntFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class UIntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<UIntFlag, UIntArgument, UInt>>,
  argument:   UIntArgument
)
  : AbstractFlagImpl<UIntFlag, UIntArgument, UInt>(longForm, shortForm, isRequired, filter, argument)
  , UIntFlag
{
  constructor(opts: FlagOptions<UInt>) : this(
    longForm   = FlagOptions<UInt>::longForm.property(opts),
    shortForm  = FlagOptions<UInt>::shortForm.property(opts),
    isRequired = FlagOptions<UInt>::required.property(opts),
    filter     = FlagOptions<UInt>::flagFilter.property(opts),
    argument   = UIntArgumentImpl(opts.argument),
  )
}
