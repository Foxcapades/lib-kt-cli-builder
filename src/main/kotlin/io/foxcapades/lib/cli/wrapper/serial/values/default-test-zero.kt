package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.CliCallComponent
import io.foxcapades.lib.cli.wrapper.ResolvedArgument
import io.foxcapades.lib.cli.wrapper.ResolvedFlag
import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import java.math.BigDecimal
import java.math.BigInteger

internal sealed class ZeroDefaultTest<P>
  : ComponentDefaultTest<Any?, P>
  where
    P : CliCallComponent
  , P : PropertyReference<*, Any?>
{
  override fun valueIsDefault(value: Any?, reference: P, config: CliSerializationConfig) =
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

internal object ArgZeroDefaultTest
  : ZeroDefaultTest<ResolvedArgument<*, Any?>>()
  , ArgumentDefaultTest<Any?>

internal object FlagZeroDefaultTest
  : ZeroDefaultTest<ResolvedFlag<*, Any?>>()
  , FlagDefaultTest<Any?>
