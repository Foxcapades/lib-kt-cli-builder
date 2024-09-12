package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.Bytes
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import java.math.BigDecimal
import java.math.BigInteger

internal object ArgNegOneFilter : ArgumentPredicate<Any?> {
  private inline val ShortNeg1: Short get() = (-1).toShort()
  private inline val IntNeg1: Int get() = -1
  private inline val LongNeg1: Long get() = -1L

  private inline val FloatNeg1: Float get() = -1.0F
  private inline val DoubleNeg1: Double get() = -1.0

  override fun shouldInclude(
    argument: Argument<Any?>,
    config:   CliSerializationConfig,
    source: ValueSource,
  ) = when {
    !argument.isSet     -> false
    argument.hasDefault -> argument.getDefault() == argument.get()
    else                -> !valueIsNegOne(argument.get())
  }

  private fun valueIsNegOne(value: Any?) =
    when (value) {
      is Int    -> value == IntNeg1
      is Double -> value == DoubleNeg1
      is Long   -> value == LongNeg1
      is Float  -> value == FloatNeg1
      is Byte   -> value == Bytes.NegOne
      is Short  -> value == ShortNeg1

      is BigDecimal -> value == BigDecimal.ONE.negate()
      is BigInteger -> value == BigInteger.ONE.negate()

      else -> false
    }
}
