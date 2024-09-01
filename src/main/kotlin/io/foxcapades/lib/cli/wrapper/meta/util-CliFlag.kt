package io.foxcapades.lib.cli.wrapper.meta

internal inline val CliFlag.hasShortForm get() = shortForm != '\u0000'

internal inline val CliFlag.hasLongForm get() = longForm != ""
