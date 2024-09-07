package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

/**
 * Standard long value formatter definitions.
 *
 * @since 1.0.0
 */
object LongFormat {
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
    ArgumentFormatter<Long> { it, c -> c.writeString(format.format(it)) }

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
    ArgumentFormatter<Long> { it, c -> c.writeString("%x".format(it)) }

  /**
   * Formats values as 16-digit hex strings.
   *
   * ```kt
   * 10   -> 000000000000000A
   * 100  -> 0000000000000064
   * 1000 -> 00000000000003E8
   * ...
   * ```
   */
  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Long> { it, c -> c.writeString("%016x".format(it)) }
}
