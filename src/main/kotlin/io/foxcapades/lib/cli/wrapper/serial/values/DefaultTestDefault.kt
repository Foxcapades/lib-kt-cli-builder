package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.meta.CliFlag
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KProperty1

// null
// number
// boolean
// string
// Collection
// Array
// Map
internal object DefaultTestDefault : DefaultValueTest {
  override fun testValue(value: Any?, annotation: CliFlag, prop: KProperty1<*, *>) = when (value) {
    null             -> true
    is Number        -> when (value) {
      is Double        -> value == DoubleZero
      is Float         -> value == FloatZero
      is BigDecimal    -> value == BigDecimal.ZERO
      is BigInteger    -> value == BigInteger.ZERO
      else             -> value.toLong() == 0L
    }
    is Boolean       -> value == false
    is Collection<*> -> value.isEmpty()
    is CharSequence  -> value.isEmpty()
    is Char          -> value == CharZero
    is Array<*>      -> value.isEmpty()
    is IntArray      -> value.isEmpty()
    is DoubleArray   -> value.isEmpty()
    is ByteArray     -> value.isEmpty()
    is CharArray     -> value.isEmpty()
    is Map<*, *>     -> value.isEmpty()
    is LongArray     -> value.isEmpty()
    is BooleanArray  -> value.isEmpty()
    is FloatArray    -> value.isEmpty()
    is ShortArray    -> value.isEmpty()
    is UInt          -> value == UIntZero
    is ULong         -> value == ULongZero
    is UByte         -> value == UByteZero
    is UShort        -> value == UShortZero
    else             -> false
  }
}