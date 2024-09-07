package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.LongArgument
import io.foxcapades.lib.cli.builder.arg.impl.LongArgumentImpl
import io.foxcapades.lib.cli.builder.flag.LongFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property


internal class LongFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<LongFlag, LongArgument, Long>>,
  argument: LongArgument
)
  : AbstractFlagImpl<LongFlag, LongArgument, Long>(longForm, shortForm, isRequired, filter, argument)
  , LongFlag
{
  constructor(opts: FlagOptions<Long>) : this(
    longForm   = FlagOptions<Long>::longForm.property(opts),
    shortForm  = FlagOptions<Long>::shortForm.property(opts),
    isRequired = FlagOptions<Long>::required.property(opts),
    filter     = FlagOptions<Long>::flagFilter.property(opts),
    argument   = LongArgumentImpl(opts.argument),
  )
}
