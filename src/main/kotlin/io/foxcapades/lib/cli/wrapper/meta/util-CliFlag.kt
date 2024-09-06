package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.util.CHAR_NULL

const val CliFlagUnsetLongForm = ""

const val CliFlagUnsetShortForm = CHAR_NULL

inline val CliFlag.hasShortForm
  get() = shortForm != CliFlagUnsetShortForm

inline val CliFlag.hasLongForm
  get() = longForm != CliFlagUnsetLongForm

@Suppress("NOTHING_TO_INLINE")
internal inline fun CliFlag.wrap() = CliFlagAnnotation(this)
