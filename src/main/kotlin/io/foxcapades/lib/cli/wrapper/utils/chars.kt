package io.foxcapades.lib.cli.wrapper.utils

internal inline val Char.isPrintable get() = isASCII && code.toByte().isPrintable