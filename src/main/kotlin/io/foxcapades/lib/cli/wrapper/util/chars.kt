package io.foxcapades.lib.cli.wrapper.util

internal inline val Char.isPrintable
  get() = isASCII && code.toByte().isPrintable

internal inline val Char.isBackSlash get() = this == '\\'

internal inline val Char.isApostrophe get() = this == '\''

internal const val CHAR_NULL = '\u0000'