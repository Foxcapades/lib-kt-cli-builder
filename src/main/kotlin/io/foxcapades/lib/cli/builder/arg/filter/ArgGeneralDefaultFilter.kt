package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.CharZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.DoubleZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.FloatZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UByteZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UIntZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.ULongZero
import io.foxcapades.lib.cli.builder.arg.filter.ArgZeroFilter.UShortZero
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.BUG
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import io.foxcapades.lib.cli.builder.util.properties.Property
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Dictionary
import java.util.Optional
import java.util.OptionalDouble
import java.util.OptionalInt
import java.util.OptionalLong
import kotlin.time.Duration

// null
// number
// boolean
// string
// Collection
// Array
// Map
internal object ArgGeneralDefaultFilter : ArgumentPredicate<Any?> {
  override fun shouldInclude(argument: Argument<Any?>, config: CliSerializationConfig, source: ValueSource) =
    when {
      !argument.isSet     -> false
      argument.hasDefault -> argument.getDefault() == argument.get()
      else                -> !valueIsDefault(argument.get())
    }

  @Suppress("NAME_SHADOWING")
  @OptIn(ExperimentalUnsignedTypes::class)
  private fun valueIsDefault(value: Any?): Boolean {
    val value = (value ?: return true)

    return when (value.javaClass.packageName) {
      null -> BUG()

      "java.lang" -> when (value) {

        is Number -> when (value) {
          is Double     -> value == DoubleZero
          is Float      -> value == FloatZero
          else          -> value.toLong() == 0L
        }

        is Boolean      -> value == false
        is CharSequence -> value.isEmpty()
        is Char         -> value == CharZero
        is Array<*>     -> value.isEmpty()
        is IntArray     -> value.isEmpty()
        is DoubleArray  -> value.isEmpty()
        is ByteArray    -> value.isEmpty()
        is CharArray    -> value.isEmpty()
        is LongArray    -> value.isEmpty()
        is BooleanArray -> value.isEmpty()
        is FloatArray   -> value.isEmpty()
        is ShortArray   -> value.isEmpty()

        else -> false
      }

      "java.util" -> when (value) {
        is Collection<*>    -> value.isEmpty()
        is Map<*, *>        -> value.isEmpty()
        is Optional<*>      -> value.isEmpty
        is OptionalDouble   -> value.isEmpty
        is OptionalInt      -> value.isEmpty
        is OptionalLong     -> value.isEmpty
        is Dictionary<*, *> -> value.isEmpty

        else -> false
      }

      "kotlin" -> when (value) {
        is UInt   -> value == UIntZero
        is ULong  -> value == ULongZero
        is UByte  -> value == UByteZero
        is UShort -> value == UShortZero

        is UIntArray   -> value == UIntZero
        is ULongArray  -> value == ULongZero
        is UByteArray  -> value == UByteZero
        is UShortArray -> value == UShortZero

        else -> false
      }

      else -> when(value) {
        is BigDecimal -> value == BigDecimal.ZERO
        is BigInteger -> value == BigInteger.ZERO

        is Property<*> -> false

        is Duration           -> value == Duration.ZERO
        is java.time.Duration -> value.isZero

        else -> false
      }
    }
  }
}
