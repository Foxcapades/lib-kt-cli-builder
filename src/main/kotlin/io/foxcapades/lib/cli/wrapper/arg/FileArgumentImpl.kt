package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.mapAbsent
import io.foxcapades.lib.cli.wrapper.util.property
import java.io.File

internal class FileArgumentImpl(
  default: Property<File>,
  isRequired: Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter: Property<ArgumentPredicate<FileArgument, File>>,
  formatter: Property<ArgumentFormatter<File>>
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
