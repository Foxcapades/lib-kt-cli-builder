package io.foxcapades.lib.cli.builder.flag

inline val CliFlag.hasShortForm
  get() = shortForm != '\u0000'

inline val CliFlag.hasLongForm
  get() = longForm != ""
