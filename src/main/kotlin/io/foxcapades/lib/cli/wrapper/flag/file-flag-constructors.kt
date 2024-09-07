package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.FlagOptions
import io.foxcapades.lib.cli.wrapper.NullableFlagOptions
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
  GeneralFlagImpl.of(NullableFlagOptions(File::class).also(action))
