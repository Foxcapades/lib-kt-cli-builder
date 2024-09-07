package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

/**
 * Standard int value formatter definitions.
 *
 * @since 1.0.0
 */
object IntFormat {
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
    ArgumentFormatter<Int> { it, c -> c.writeString(format.format(it.toLong())) }

  /**
   * Formats values in the fewest hex digits possible.
   *
   * ```kt
   * 10   -> A
   * 100  -> 64
   * 1000 -> 3E8
   * ...
   * ```
   */
  @JvmStatic
  inline val MinimalHex get() =
    ArgumentFormatter<Int> { it, c -> c.writeString("%x".format(it)) }

  /**
   * Formats values as 8-digit hex strings.
   *
   * ```kt
   * 10   -> 0000000A
   * 100  -> 00000064
   * 1000 -> 000003E8
   * ...
   * ```
   */
  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Int> { it, c -> c.writeString("%08x".format(it)) }
}
