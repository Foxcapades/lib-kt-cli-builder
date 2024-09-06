package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.ArgOptions
import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.NullableArgOptions
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.util.*
import java.nio.file.Path

interface PathArgument : ComplexArgument<Path>

fun pathArg(action: ArgOptions<Path>.() -> Unit): PathArgument =
  PathArgumentImpl(ArgOptions(Path::class).also(action))

fun nullablePathArg(action: NullableArgOptions<Path>.() -> Unit): Argument<Path?> =
  GeneralArgumentImpl.of(NullableArgOptions(Path::class).also {
    it.action()
    NullableArgOptions<Path>::shouldQuote.property<Boolean>(it).setIfAbsent(true)
  })

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
