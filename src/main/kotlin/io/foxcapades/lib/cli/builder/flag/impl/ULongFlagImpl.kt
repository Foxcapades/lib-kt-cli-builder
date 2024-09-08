package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.ULongArgument
import io.foxcapades.lib.cli.builder.arg.impl.ULongArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.ULongFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class ULongFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ULongFlag, ULongArgument, ULong>>,
  argument:   ULongArgument
)
  : AbstractFlagImpl<ULongFlag, ULongArgument, ULong>(longForm, shortForm, isRequired, filter, argument)
  , ULongFlag
{
  constructor(opts: FlagOptions<ULong>) : this(
    longForm   = FlagOptions<ULong>::longForm.property(opts),
    shortForm  = FlagOptions<ULong>::shortForm.property(opts),
    isRequired = FlagOptions<ULong>::required.property(opts),
    filter     = FlagOptions<ULong>::flagFilter.property(opts),
    argument   = ULongArgumentImpl(opts.argument),
  )
}
