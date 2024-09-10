package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

/**
 * Standard short value formatter definitions.
 *
 * @since 1.0.0
 */
object ShortFormat {
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
    ArgumentFormatter<Short> { it, c, _ -> c.writeString(format.format(it.toLong())) }

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
  inline val MinimalHex
    get() = ArgumentFormatter<Short> { it, c, _ -> c.writeString("%x".format(it)) }

  /**
   * Formats value as 4-digit hex strings.
   *
   * ```kt
   * 10   -> 000A
   * 100  -> 0064
   * 1000 -> 03E8
   * ...
   * ```
   */
  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Short> { it, c, _ -> c.writeString("%04x".format(it)) }
}
