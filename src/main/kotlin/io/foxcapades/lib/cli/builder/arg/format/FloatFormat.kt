package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

/**
 * Standard float value formatter definitions.
 *
 * @since 1.0.0
 */
object FloatFormat {
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
    ArgumentFormatter<Float> { it, c -> c.writeString(format.format(it.toDouble())) }

  /**
   * Formats float values in the default region-based currency format.
   */
  @JvmStatic
  inline val Currency get() =
    of(NumberFormat.getCurrencyInstance())
}
