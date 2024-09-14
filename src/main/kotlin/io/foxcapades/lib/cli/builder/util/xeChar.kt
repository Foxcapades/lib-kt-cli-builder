package io.foxcapades.lib.cli.builder.util

internal inline val Char.isPrintable
  get() = isASCII && code.toByte().isPrintable
