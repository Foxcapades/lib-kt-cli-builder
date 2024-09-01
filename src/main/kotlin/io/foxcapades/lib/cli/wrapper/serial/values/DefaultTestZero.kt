package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KProperty1

internal object DefaultTestZero : DefaultValueTest {
  override fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) =
    when (value) {
      is Int    -> value == IntZero
      is Double -> value == DoubleZero
      is Long   -> value == LongZero
      is Float  -> value == FloatZero
      is Byte   -> value == ByteZero
      is Short  -> value == ShortZero

      is BigDecimal -> value == BigDecimal.ZERO
      is BigInteger -> value == BigInteger.ZERO

      is UInt   -> value == UIntZero
      is ULong  -> value == ULongZero
      is UByte  -> value == UByteZero
      is UShort -> value == UShortZero

      is Char -> value == CharZero

      else -> false
    }
}