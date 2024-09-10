package io.foxcapades.lib.cli.builder.arg.format

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Standard double value formatter definitions.
 *
 * @since 1.0.0
 */
@Suppress("NOTHING_TO_INLINE")
object DoubleFormat {
  /**
   * Creates a new [ArgumentFormatter] instance fromm the given [NumberFormat].
   *
   * @param format Number format instance.
   *
   * @return An `ArgumentFormatter` using the given `NumberFormat`.
   */
  @JvmStatic
  inline fun of(format: NumberFormat) =
    ArgumentFormatter<Double> { it, c, _ -> c.writeString(format.format(it)) }

  @JvmStatic
  inline fun ofFormat(format: String) = of(DecimalFormat(format))

  /**
   * Formats double values in the default region-based currency format.
   */
  @JvmStatic
  inline val Currency get() =
    of(NumberFormat.getCurrencyInstance())
}
