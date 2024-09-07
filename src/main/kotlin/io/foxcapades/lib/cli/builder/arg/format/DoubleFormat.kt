package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object DoubleFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) =
    ArgumentFormatter<Double> { it, c -> c.writeString(formatter.format(it)) }

  @JvmStatic
  inline val Currency get() =
    of(NumberFormat.getCurrencyInstance())
}
