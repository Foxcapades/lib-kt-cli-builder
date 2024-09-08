package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.ShortArgument
import io.foxcapades.lib.cli.builder.arg.impl.ShortArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.ShortFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class ShortFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ShortFlag, ShortArgument, Short>>,
  argument:   ShortArgument
)
  : AbstractFlagImpl<ShortFlag, ShortArgument, Short>(longForm, shortForm, isRequired, filter, argument)
  , ShortFlag
{
  constructor(opts: FlagOptions<Short>) : this(
    longForm   = FlagOptions<Short>::longForm.property(opts),
    shortForm  = FlagOptions<Short>::shortForm.property(opts),
    isRequired = FlagOptions<Short>::required.property(opts),
    filter     = FlagOptions<Short>::flagFilter.property(opts),
    argument   = ShortArgumentImpl(opts.argument),
  )
}
