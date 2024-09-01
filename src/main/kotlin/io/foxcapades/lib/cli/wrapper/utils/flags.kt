@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.wrapper.utils

internal inline val Char.isValidShortFlag
  get() = isASCII && code.toByte().isValidShortFlag

internal inline fun Char.isValidShortFlag(prefix: Char) =
  this != prefix && isValidShortFlag

internal inline val Byte.isValidShortFlag
  get() = isLowerAlpha
    || isUpperAlpha
    || isDigit
    || this == ASCII_PERCENT
    || this in ASCII_PLUS .. ASCII_SOLIDUS
    || this == ASCII_COLON
    || this == ASCII_EQUALS
    || this == ASCII_COMMERCIAL_AT
    || this == ASCII_CARET
    || this == ASCII_UNDERSCORE

internal inline fun Byte.isValidShortFlag(prefix: Byte) =
  this != prefix && isValidShortFlag

internal inline val String.isValidLongFlag
  get() = !(isBlank() || any { !it.isValidShortFlag })

internal inline fun String.isValidLongFlag(delimiter: Char) =
  !(isBlank() || any { it == delimiter || !it.isValidShortFlag })
