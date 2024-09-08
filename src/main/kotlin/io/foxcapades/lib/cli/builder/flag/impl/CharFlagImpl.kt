package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.CharArgument
import io.foxcapades.lib.cli.builder.arg.impl.CharArgumentImpl
import io.foxcapades.lib.cli.builder.flag.CharFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class CharFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<CharFlag, CharArgument, Char>>,
  argument:   CharArgument
)
  : AbstractFlagImpl<CharFlag, CharArgument, Char>(longForm, shortForm, isRequired, filter, argument)
  , CharFlag
{
  constructor(opts: FlagOptions<Char>) : this(
    longForm   = FlagOptions<Char>::longForm.property(opts),
    shortForm  = FlagOptions<Char>::shortForm.property(opts),
    isRequired = FlagOptions<Char>::required.property(opts),
    filter     = FlagOptions<Char>::flagFilter.property(opts),
    argument   = CharArgumentImpl(opts.argument)
  )
}
