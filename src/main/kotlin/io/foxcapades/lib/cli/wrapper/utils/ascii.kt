@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.wrapper.utils

internal inline val Byte.isLowerAlpha get() = this in 97..122

internal inline val Byte.isUpperAlpha get() = this in 65..90

internal inline val Byte.isDigit get() = this in 48..57

internal inline val Byte.isPrintable get() = this in 32 .. 126

internal const val ASCII_PERCENT: Byte = 37
internal const val ASCII_PLUS: Byte = 43
internal const val ASCII_COMMA: Byte = 44
internal const val ASCII_HYPHEN: Byte = 45
internal const val ASCII_PERIOD: Byte = 46
internal const val ASCII_SOLIDUS: Byte = 47
internal const val ASCII_COLON: Byte = 58
internal const val ASCII_EQUALS: Byte = 61
internal const val ASCII_COMMERCIAL_AT: Byte = 64
internal const val ASCII_CARET: Byte = 94
internal const val ASCII_UNDERSCORE: Byte = 95

internal inline val Char.isASCII get() = code in 0..127

internal inline val Char.isUppercase get() = this in 'A' .. 'Z'

internal inline val Char.isLowercase get() = this in 'a' .. 'z'

internal inline fun Char.toLowercase() = if (isUppercase) this + 32 else this

internal inline fun Char.toUppercase() = if (isLowercase) this - 32 else this
