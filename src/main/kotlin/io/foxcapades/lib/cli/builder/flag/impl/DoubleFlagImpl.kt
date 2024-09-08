package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.DoubleArgument
import io.foxcapades.lib.cli.builder.arg.impl.DoubleArgumentImpl
import io.foxcapades.lib.cli.builder.flag.DoubleFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property


internal class DoubleFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<DoubleFlag, DoubleArgument, Double>>,
  argument:   DoubleArgument
)
  : AbstractFlagImpl<DoubleFlag, DoubleArgument, Double>(longForm, shortForm, isRequired, filter, argument)
  , DoubleFlag
{
  constructor(opts: FlagOptions<Double>) : this(
    longForm   = FlagOptions<Double>::longForm.property(opts),
    shortForm  = FlagOptions<Double>::shortForm.property(opts),
    isRequired = FlagOptions<Double>::required.property(opts),
    filter     = FlagOptions<Double>::flagFilter.property(opts),
    argument   = DoubleArgumentImpl(opts.argument),
  )
}
