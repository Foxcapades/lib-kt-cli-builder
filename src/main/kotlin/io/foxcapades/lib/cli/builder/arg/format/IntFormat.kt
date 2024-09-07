package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object IntFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) =
    ArgumentFormatter<Int> { it, c -> c.writeString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() =
    ArgumentFormatter<Int> { it, c -> c.writeString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Int> { it, c -> c.writeString("%08x".format(it)) }
}
