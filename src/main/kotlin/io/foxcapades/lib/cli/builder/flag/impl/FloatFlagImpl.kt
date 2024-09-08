package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.FloatArgument
import io.foxcapades.lib.cli.builder.arg.impl.FloatArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.FloatFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class FloatFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<FloatFlag, FloatArgument, Float>>,
  argument:   FloatArgument
)
  : AbstractFlagImpl<FloatFlag, FloatArgument, Float>(longForm, shortForm, isRequired, filter, argument)
  , FloatFlag
{
  constructor(opts: FlagOptions<Float>) : this(
    longForm   = FlagOptions<Float>::longForm.property(opts),
    shortForm  = FlagOptions<Float>::shortForm.property(opts),
    isRequired = FlagOptions<Float>::required.property(opts),
    filter     = FlagOptions<Float>::flagFilter.property(opts),
    argument   = FloatArgumentImpl(opts.argument),
  )
}
