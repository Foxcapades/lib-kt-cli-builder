package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.CharZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.DoubleZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.FloatZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UByteZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UIntZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.ULongZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UShortZero
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KCallable

// null
// number
// boolean
// string
// Collection
// Array
// Map
internal object ArgGeneralDefaultFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
    config: CliSerializationConfig,
  ) =
    when {
      !argument.isSet     -> false
      argument.hasDefault -> argument.getDefault() == argument.get()
      else                -> !valueIsDefault(argument.get())
    }

  private fun valueIsDefault(value: Any?) =
    when (value) {
      null             -> true
      is Number        -> when (value) {
        is Double      -> value == DoubleZero
        is Float       -> value == FloatZero
        is BigDecimal  -> value == BigDecimal.ZERO
        is BigInteger  -> value == BigInteger.ZERO
        else           -> value.toLong() == 0L
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
