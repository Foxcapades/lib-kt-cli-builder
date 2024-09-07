package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.FileFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import java.io.File

@Suppress("NOTHING_TO_INLINE")
inline fun fileFlag(longForm: String, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun fileFlag(shortForm: Char, noinline action: FlagOptions<File>.() -> Unit = {}) =
  fileFlag { this.shortForm = shortForm; action() }

fun fileFlag(action: FlagOptions<File>.() -> Unit = {}): FileFlag =
  FileFlagImpl(FlagOptions(File::class).also(action))

fun nullableFileFlag(action: NullableFlagOptions<File>.() -> Unit = {}): Flag<Argument<File?>, File?> =
  UniversalFlagImpl.of(NullableFlagOptions(File::class).also(action))
