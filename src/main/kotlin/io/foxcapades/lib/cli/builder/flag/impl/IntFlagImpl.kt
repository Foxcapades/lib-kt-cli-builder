package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.IntArgument
import io.foxcapades.lib.cli.builder.arg.impl.IntArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.IntFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class IntFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<IntFlag, IntArgument, Int>>,
  argument:   IntArgument
)
  : AbstractFlagImpl<IntFlag, IntArgument, Int>(longForm, shortForm, isRequired, filter, argument)
  , IntFlag
{
  constructor(opts: FlagOptions<Int>) : this(
    longForm   = FlagOptions<Int>::longForm.property(opts),
    shortForm  = FlagOptions<Int>::shortForm.property(opts),
    isRequired = FlagOptions<Int>::required.property(opts),
    filter     = FlagOptions<Int>::flagFilter.property(opts),
    argument   = IntArgumentImpl(opts.argument),
  )
}
