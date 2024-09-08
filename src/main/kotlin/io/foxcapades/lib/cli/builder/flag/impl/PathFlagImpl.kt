package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.PathArgument
import io.foxcapades.lib.cli.builder.arg.impl.PathArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.PathFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import java.nio.file.Path

internal class PathFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<PathFlag, PathArgument, Path>>,
  argument:   PathArgument
)
  : AbstractFlagImpl<PathFlag, PathArgument, Path>(longForm, shortForm, isRequired, filter, argument)
  , PathFlag
{
  constructor(opts: FlagOptions<Path>) : this(
    longForm   = FlagOptions<Path>::longForm.property(opts),
    shortForm  = FlagOptions<Path>::shortForm.property(opts),
    isRequired = FlagOptions<Path>::required.property(opts),
    filter     = FlagOptions<Path>::flagFilter.property(opts),
    argument   = PathArgumentImpl(opts.argument),
  )
}
