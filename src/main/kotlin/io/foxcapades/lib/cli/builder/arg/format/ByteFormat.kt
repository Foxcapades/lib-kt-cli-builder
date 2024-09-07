package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

/**
 * Standard byte value formatter definitions.
 *
 * @since 1.0.0
 */
object ByteFormat {
  /**
   * Creates a new [ArgumentFormatter] instance fromm the given [NumberFormat].
   *
   * @param format Number format instance.
   *
   * @return An `ArgumentFormatter` using the given `NumberFormat`.
   */
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(format: NumberFormat) =
    ArgumentFormatter<Byte> { it, c -> c.writeString(format.format(it.toLong())) }

  /**
   * Formats a byte value in the fewest hex digits possible.
   *
   * ```kt
   * 10  -> A
   * 100 -> 64
   * ```
   */
  @JvmStatic
  inline val MinimalHex get() =
    ArgumentFormatter<Byte> { it, c -> c.writeString("%x".format(it)) }

  /**
   * Formats a byte value as a 2-digit hex string.
   *
   * ```kt
   * 10  -> 0A
   * 100 -> 64
   * ```
   */
  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Byte> { it, c -> c.writeString("%02x".format(it)) }

  /**
   * Formats a byte value as an ASCII character.
   */
  @JvmStatic
  inline val ASCII get() =
    ArgumentFormatter<Byte> { it, c -> c.writeChar(it) }
}
