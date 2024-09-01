package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KProperty1

internal object DefaultTestNegativeOne : DefaultValueTest {
  override fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) =
    when (value) {
      is Int    -> value == IntNeg1
      is Double -> value == DoubleNeg1
      is Long   -> value == LongNeg1
      is Float  -> value == FloatNeg1
      is Byte   -> value == ByteNeg1
      is Short  -> value == ShortNeg1

      is BigDecimal -> value == BigDecimal.ONE.negate()
      is BigInteger -> value == BigInteger.ONE.negate()

      else -> false
    }
}