package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.ByteArgument
import io.foxcapades.lib.cli.builder.arg.impl.ByteArgumentImpl
import io.foxcapades.lib.cli.builder.flag.ByteFlag
import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class ByteFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ByteFlag, ByteArgument, Byte>>,
  argument:   ByteArgument
)
  : AbstractFlagImpl<ByteFlag, ByteArgument, Byte>(longForm, shortForm, isRequired, filter, argument)
  , ByteFlag
{
  constructor(opts: FlagOptions<Byte>) : this(
    longForm   = FlagOptions<Byte>::longForm.property(opts),
    shortForm  = FlagOptions<Byte>::shortForm.property(opts),
    isRequired = FlagOptions<Byte>::required.property(opts),
    filter     = FlagOptions<Byte>::flagFilter.property(opts),
    argument   = ByteArgumentImpl(opts.argument)
  )
}
