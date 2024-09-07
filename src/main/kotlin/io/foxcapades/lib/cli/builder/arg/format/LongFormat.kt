package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object LongFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) =
    ArgumentFormatter<Long> { it, c -> c.writeString(formatter.format(it)) }

  @JvmStatic
  inline val MinimalHex get() =
    ArgumentFormatter<Long> { it, c -> c.writeString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Long> { it, c -> c.writeString("%016x".format(it)) }
}
