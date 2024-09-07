package io.foxcapades.lib.cli.builder.util

internal object Bytes {
  inline val NegOne: Byte
    get() = -1

  inline val Zero: Byte
    get() = 0

  inline val One: Byte
    get() = 1

  inline val Two: Byte
    get() = 2

  @Suppress("NOTHING_TO_INLINE")
  inline fun equal(l: Byte, r: Byte) = l == r
}
