package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.PathFlagImpl
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import java.nio.file.Path

@Suppress("NOTHING_TO_INLINE")
inline fun pathFlag(longForm: String, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.longForm = longForm; action() }

@Suppress("NOTHING_TO_INLINE")
inline fun pathFlag(shortForm: Char, noinline action: FlagOptions<Path>.() -> Unit = {}) =
  pathFlag { this.shortForm = shortForm; action() }

fun pathFlag(action: FlagOptions<Path>.() -> Unit = {}): PathFlag =
  PathFlagImpl(FlagOptions(Path::class).also(action))

fun nullablePathFlag(action: NullableFlagOptions<Path>.() -> Unit = {}): Flag<Argument<Path?>, Path?> =
  UniversalFlagImpl.of(NullableFlagOptions(Path::class).also(action))
