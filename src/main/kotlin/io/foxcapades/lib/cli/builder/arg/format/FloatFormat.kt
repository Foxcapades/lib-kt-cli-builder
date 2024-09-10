package io.foxcapades.lib.cli.builder.arg.format

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Standard float value formatter definitions.
 *
 * @since 1.0.0
 */
@Suppress("NOTHING_TO_INLINE")
object FloatFormat {
  /**
   * Creates a new [ArgumentFormatter] instance fromm the given [NumberFormat].
   *
   * @param format Number format instance.
   *
   * @return An `ArgumentFormatter` using the given `NumberFormat`.
   */
  @JvmStatic
  inline fun of(format: NumberFormat) =
    ArgumentFormatter<Float> { it, c, _ -> c.writeString(format.format(it.toDouble())) }

  @JvmStatic
  inline fun ofFormat(format: String) = DoubleFormat.of(DecimalFormat(format))

  /**
   * Formats float values in the default region-based currency format.
   */
  @JvmStatic
  inline val Currency get() =
    of(NumberFormat.getCurrencyInstance())
}
