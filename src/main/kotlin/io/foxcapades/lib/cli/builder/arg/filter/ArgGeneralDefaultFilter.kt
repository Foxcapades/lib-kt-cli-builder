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
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.time.Duration

// null
// number
// boolean
// string
// Collection
// Array
// Map
/**
 * Generalized Defaulted Argument Filter
 *
 * Attempts to filter out arguments that are set to their "default" value.
 *
 * This filter performs up to 3 types of tests on a given argument to try and
 * determine whether the [Argument] should be considered as having its "default"
 * value.
 *
 * 1. Is set: if a given argument is not set, the filter will return
 * `false` for that argument.
 * 2. Is configured default: if a given, set argument has a configured default
 * value, and the currently set value is equal to that default, the filter will
 * return `false` for that argument.
 * 3. Is default for type: if a given, set argument does not have a configured
 * default value, the filter will attempt to guess at the default for a small
 * set of types, primarily consisting of primitives, strings, and collections by
 * comparing the argument value to the 'zero' value for that type.
 *
 * ### Type Based Default Tests
 *
 * The following types will be tested for their 'zero' value:
 *
 * | Type       | Default If | Includes                                                                |
 * |------------|------------|-------------------------------------------------------------------------|
 * | Number     | `0`        | all primitive number types, kotlin unsigned types, and `char`
 * | Boolean    | `false`    | Boolean
 * | String     | empty      | CharSequence, String
 * | Array      | empty      | All array types including primitive arrays and kotlin unsigned arrays
 * | Collection | empty      |
 * | Map        | empty      |
 * | Optional   | empty      | All Java Optional types including primitive options
 * | Duration   | `0`        | Both Java and Kotlin durations
 * | Property   | empty      | cli-builder Property implementations and subtypes
 *
 * @since 1.0.0
 */
internal object ArgGeneralDefaultFilter : ArgumentPredicate<Any?> {
  override fun shouldInclude(argument: Argument<Any?>, config: CliSerializationConfig, source: ValueSource) =
    when {
      !argument.isSet     -> false
      argument.hasDefault -> argument.getDefault() != argument.get()
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

        is UIntArray   -> value.isEmpty()
        is ULongArray  -> value.isEmpty()
        is UByteArray  -> value.isEmpty()
        is UShortArray -> value.isEmpty()

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
