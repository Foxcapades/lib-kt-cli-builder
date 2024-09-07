package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.StringArgument
import io.foxcapades.lib.cli.builder.arg.impl.StringArgumentImpl
import io.foxcapades.lib.cli.builder.flag.StringFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property

internal class StringFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<StringFlag, StringArgument, String>>,
  argument:   StringArgument
)
  : AbstractFlagImpl<StringFlag, StringArgument, String>(longForm, shortForm, isRequired, filter, argument)
  , StringFlag
{
  constructor(opts: FlagOptions<String>) : this(
    longForm   = FlagOptions<String>::longForm.property(opts),
    shortForm  = FlagOptions<String>::shortForm.property(opts),
    isRequired = FlagOptions<String>::required.property(opts),
    filter     = FlagOptions<String>::flagFilter.property(opts),
    argument   = StringArgumentImpl(opts.argument),
  )
}
