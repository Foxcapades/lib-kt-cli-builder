package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.PathArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.property
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.mapAbsent
import java.nio.file.Path

internal class PathArgumentImpl(
  default:     Property<Path>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<PathArgument, Path>>,
  formatter:   Property<ArgumentFormatter<Path>>
) : ComplexArgumentImpl<PathArgument, Path>(
  default     = default,
  isRequired  = isRequired,
  shouldQuote = shouldQuote,
  filter      = filter,
  formatter   = formatter.mapAbsent { ArgumentFormatter(Path::toString) }
), PathArgument {
  constructor(opts: ArgOptions<Path>) : this(
    default     = ArgOptions<Path>::default.property(opts),
    isRequired  = ArgOptions<Path>::required.property(opts),
    shouldQuote = ArgOptions<Path>::shouldQuote.property(opts),
    formatter   = ArgOptions<Path>::formatter.property(opts),
    filter      = ArgOptions<Path>::filter.property(opts),
  )
}
