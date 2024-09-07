package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.UShortArgument
import io.foxcapades.lib.cli.builder.arg.impl.UShortArgumentImpl
import io.foxcapades.lib.cli.builder.flag.UShortFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property

internal class UShortFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<UShortFlag, UShortArgument, UShort>>,
  argument:   UShortArgument
)
  : AbstractFlagImpl<UShortFlag, UShortArgument, UShort>(longForm, shortForm, isRequired, filter, argument)
  , UShortFlag
{
  constructor(opts: FlagOptions<UShort>) : this(
    longForm   = FlagOptions<UShort>::longForm.property(opts),
    shortForm  = FlagOptions<UShort>::shortForm.property(opts),
    isRequired = FlagOptions<UShort>::required.property(opts),
    filter     = FlagOptions<UShort>::flagFilter.property(opts),
    argument   = UShortArgumentImpl(opts.argument),
  )
}
