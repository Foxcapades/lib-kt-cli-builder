package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.FileArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.mapAbsent
import java.io.File

internal class FileArgumentImpl(
  default:     Property<File>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<FileArgument, File>>,
  formatter:   Property<ArgumentFormatter<File>>
) : ComplexArgumentImpl<FileArgument, File>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote,
  filter      = filter,
  formatter   = formatter.mapAbsent { ArgumentFormatter(File::toString) }
), FileArgument {
  constructor(opts: ArgOptions<File>) : this(
    default     = ArgOptions<File>::default.property(opts),
    isRequired  = ArgOptions<File>::required.property(opts),
    shouldQuote = ArgOptions<File>::shouldQuote.property(opts),
    formatter   = ArgOptions<File>::formatter.property(opts),
    filter      = ArgOptions<File>::filter.property(opts),
  )
}
