package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.FileArgument
import io.foxcapades.lib.cli.wrapper.arg.FileArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.property
import java.io.File

interface FileFlag : ComplexFlag<FileArgument, File>

@Suppress("NOTHING_TO_INLINE")
inline fun fileFlag(longForm: String, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun fileFlag(shortForm: Char, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.shortForm = shortForm; action() }

fun fileFlag(action: FlagOptions<File>.() -> Unit = {}): FileFlag =
  FileFlagImpl(FlagOptions(File::class).also(action))

fun nullableFileFlag(action: NullableFlagOptions<File>.() -> Unit = {}): Flag<Argument<File?>, File?> =
  GeneralFlagImpl.of(NullableFlagOptions(File::class).also(action))

internal class FileFlagImpl(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<FileFlag, FileArgument, File>>,
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
