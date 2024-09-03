package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.CliCallComponent
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import java.math.BigDecimal
import java.math.BigInteger

internal sealed class NegativeOneDefaultTest<P>
  : ComponentDefaultTest<Any?, P>
  where
    P : CliCallComponent
  , P : PropertyReference<*, Any?>
{
  override fun valueIsDefault(value: Any?, reference: P, config: CliSerializationConfig) =
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

internal data object ArgNegativeOneDefaultTest : NegativeOneDefaultTest<ResolvedArgument<*, Any?>>(), ArgumentDefaultTest<Any?>

internal data object FlagNegativeOneDefaultTest : NegativeOneDefaultTest<ResolvedFlag<*, Any?>>(), FlagDefaultTest<Any?>
