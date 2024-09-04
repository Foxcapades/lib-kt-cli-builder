package io.foxcapades.lib.cli.wrapper.serial.values

import java.text.NumberFormat

object BooleanFormat {
  @JvmStatic
  inline val YesNoUpper get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "YES" else "NO") }

  @JvmStatic
  inline val YesNoTitle get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "Yes" else "No") }

  @JvmStatic
  inline val YesNoLower get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "yes" else "no") }

  @JvmStatic
  inline val Binary get() = ArgumentFormatter<Boolean> { it, c -> c.appendChar(if (it) '1' else '0') }

  @JvmStatic
  inline val TrueFalseUpper get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "TRUE" else "FALSE") }

  @JvmStatic
  inline val TrueFalseTitle get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "True" else "False") }

  @JvmStatic
  inline val TrueFalseLower get() = ArgumentFormatter<Boolean> { it, c -> c.appendString(if (it) "true" else "false") }
}

object ByteFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Byte> { it, c -> c.appendString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() = ArgumentFormatter<Byte> { it, c -> c.appendString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Byte> { it, c -> c.appendString("%02x".format(it)) }

  @JvmStatic
  inline val ASCII get() = ArgumentFormatter<Byte> { it, c -> c.appendChar(it) }
}

object DoubleFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Double> { it, c -> c.appendString(formatter.format(it)) }

  @JvmStatic
  inline val Currency get() = of(NumberFormat.getCurrencyInstance())
}

object FloatFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Float> { it, c -> c.appendString(formatter.format(it.toDouble())) }

  @JvmStatic
  inline val Currency get() = of(NumberFormat.getCurrencyInstance())
}

object IntFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Int> { it, c -> c.appendString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() = ArgumentFormatter<Int> { it, c -> c.appendString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Int> { it, c -> c.appendString("%08x".format(it)) }
}

object LongFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Long> { it, c -> c.appendString(formatter.format(it)) }

  @JvmStatic
  inline val MinimalHex get() = ArgumentFormatter<Long> { it, c -> c.appendString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Long> { it, c -> c.appendString("%016x".format(it)) }
}

object ShortFormat {
  @JvmStatic
  @Suppress("NOTHING_TO_INLINE")
  inline fun of(formatter: NumberFormat) = ArgumentFormatter<Short> { it, c -> c.appendString(formatter.format(it.toLong())) }

  @JvmStatic
  inline val MinimalHex get() = ArgumentFormatter<Short> { it, c -> c.appendString("%x".format(it)) }

  @JvmStatic
  inline val HexPadded get() = ArgumentFormatter<Short> { it, c -> c.appendString("%04x".format(it)) }
}
