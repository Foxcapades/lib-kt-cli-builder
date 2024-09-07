package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.flag.FlagOptions
import io.foxcapades.lib.cli.builder.arg.FileArgument
import io.foxcapades.lib.cli.builder.arg.impl.FileArgumentImpl
import io.foxcapades.lib.cli.builder.flag.FileFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.reflect.property
import java.io.File


internal class FileFlagImpl(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  filter: Property<FlagPredicate<FileFlag, FileArgument, File>>,
  argument:   FileArgument
)
  : AbstractFlagImpl<FileFlag, FileArgument, File>(longForm, shortForm, isRequired, filter, argument)
  , FileFlag
{
  constructor(opts: FlagOptions<File>) : this(
    longForm   = FlagOptions<File>::longForm.property(opts),
    shortForm  = FlagOptions<File>::shortForm.property(opts),
    isRequired = FlagOptions<File>::required.property(opts),
    filter     = FlagOptions<File>::flagFilter.property(opts),
    argument   = FileArgumentImpl(opts.argument),
  )
}
