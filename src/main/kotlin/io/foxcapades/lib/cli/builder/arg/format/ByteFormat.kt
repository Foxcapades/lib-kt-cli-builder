package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object ByteFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) =
    ArgumentFormatter<Byte> { it, c -> c.writeString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() =
    ArgumentFormatter<Byte> { it, c -> c.writeString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() =
    ArgumentFormatter<Byte> { it, c -> c.writeString("%02x".format(it)) }

  @JvmStatic
  inline val ASCII get() =
    ArgumentFormatter<Byte> { it, c -> c.writeChar(it) }
}
