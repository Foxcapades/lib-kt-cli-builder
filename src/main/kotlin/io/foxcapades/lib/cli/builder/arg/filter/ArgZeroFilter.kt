package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.Bytes
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KCallable

internal object ArgZeroFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  internal inline val ShortZero: Short get() = 0.toShort()
  internal inline val IntZero: Int get() = 0
  internal inline val LongZero: Long get() = 0L

  internal inline val FloatZero: Float get() = 0.0F
  internal inline val DoubleZero: Double get() = 0.0

  internal inline val UByteZero: UByte get() = 0.toUByte()
  internal inline val UShortZero: UShort get() = 0.toUShort()
  internal inline val UIntZero: UInt get() = 0u
  internal inline val ULongZero: ULong get() = 0uL

  internal inline val CharZero: Char get() = '\u0000'

  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ValueAccessorReference<*, Any?, out KCallable<Any?>>,
    config: CliSerializationConfig
  ) = when {
    !argument.isSet     -> false
    argument.hasDefault -> argument.getDefault() == argument.get()
    else                -> !valueIsZero(argument.get())
  }

  private fun valueIsZero(value: Any?) =
    when (value) {
      is Int    -> value == IntZero
      is Double -> value == DoubleZero
      is Long   -> value == LongZero
      is Float  -> value == FloatZero
      is Byte   -> value == Bytes.Zero
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
