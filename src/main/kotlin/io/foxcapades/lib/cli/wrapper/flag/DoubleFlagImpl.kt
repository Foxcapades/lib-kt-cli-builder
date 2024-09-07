package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.arg.DoubleArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property


internal class DoubleFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<DoubleFlag, DoubleArgument, Double>>,
  argument: DoubleArgument
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
