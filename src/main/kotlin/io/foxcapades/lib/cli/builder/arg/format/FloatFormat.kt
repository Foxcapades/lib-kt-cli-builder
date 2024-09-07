package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object FloatFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) =
    ArgumentFormatter<Float> { it, c -> c.writeString(formatter.format(it.toDouble())) }

  @JvmStatic
  inline val Currency get() =
    of(NumberFormat.getCurrencyInstance())
}
