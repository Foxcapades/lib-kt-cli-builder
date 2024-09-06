package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.PathArgument
import io.foxcapades.lib.cli.wrapper.arg.PathArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import java.nio.file.Path

interface PathFlag : ComplexFlag<PathArgument, Path>

@Suppress("NOTHING_TO_INLINE")
inline fun pathFlag(longForm: String, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun pathFlag(shortForm: Char, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.shortForm = shortForm; action() }

fun pathFlag(action: FlagOptions<Path>.() -> Unit = {}): PathFlag =
  PathFlagImpl(FlagOptions(Path::class).also(action))

fun nullablePathFlag(action: NullableFlagOptions<Path>.() -> Unit = {}): Flag<Argument<Path?>, Path?> =
  GeneralFlagImpl.of(NullableFlagOptions(Path::class).also(action))

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
