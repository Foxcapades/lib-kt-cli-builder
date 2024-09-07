@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util

internal inline val Byte.isPrintable
  get() = this in 32 .. 126

internal inline val Char.isASCII
  get() = code in 0..127

internal inline val Char.isUppercase
  get() = this in 'A' .. 'Z'

internal inline val Char.isLowercase
  get() = this in 'a' .. 'z'

internal inline fun Char.toLowercase() =
  if (isUppercase) this + 32 else this

internal inline fun Char.toUppercase() =
  if (isLowercase) this - 32 else this
