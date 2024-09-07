package io.foxcapades.lib.cli.builder.arg.format

import java.text.NumberFormat

object ShortFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Short> { it, c -> c.writeString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() = ArgumentFormatter<Short> { it, c -> c.writeString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Short> { it, c -> c.writeString("%04x".format(it)) }
}
