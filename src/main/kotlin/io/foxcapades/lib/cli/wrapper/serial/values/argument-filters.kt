package io.foxcapades.lib.cli.wrapper.serial.values

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import java.math.BigDecimal
import java.math.BigInteger

/**
 * # Argument Inclusion Predicate
 *
 * Defines a predicate function used to determine whether an [Argument] or its
 * parent should be included in CLI call serialization output based on the value
 * of that `Argument`.
 *
 * This predicate is only called on `Argument` instances that either have a
 * default value or have been explicitly set.
 *
 * A base implementation which simply includes the value if it is not equal to
 * a configured default:
 *
 * ```kt
 * predicate = { v, a, _, _ -> a.hasDefault && v == a.default }
 * ```
 *
 * @param V Type of value to be tested.
 *
 * @param A Argument type.
 *
 * @since 1.0.0
 */
fun interface ArgumentPredicate<A : Argument<V>, V> {
  /**
   * Called to test whether the given argument should be included in the
   * serialized CLI call output.
   *
   * This function will only be called on arguments that either have a default
   * value or have been explicitly set.
   *
   * @param argument Argument instance containing the value under test.
   *
   * @param reference Reference to the class property or method from which the
   * argument was obtained.
   *
   * @param config Current serialization config.
   *
   * @return `true` if the value should be included in the serialized CLI call
   * output, otherwise `false`.
   */
  fun shouldInclude(
    argument: A,
    reference: ResolvedComponent<*, V>,
    config: CliSerializationConfig
  ): Boolean

  companion object {
    /**
     * Convenience method for defining a simple, value-based [ArgumentPredicate]
     * instance.
     *
     * Example:
     * ```kt
     * // Simple predicate that only includes arguments whose value equals
     * // `true`.
     * ArgumentPredicate.simple { it == true }
     * ```
     *
     * @param V Type of value to be tested.
     *
     * @param A Argument type.
     *
     * @param pred Actual predicate implementation that will be passed a target
     * value to determine whether its containing argument should be included in
     * the serialized CLI call.
     *
     * @return An `ArgumentPredicate` instance wrapping the given
     * [predicate][pred].
     */
    @JvmStatic
    inline fun <A : Argument<V>, V> simple(crossinline pred: (V) -> Boolean) =
      ArgumentPredicate<A, V> { a, _, _ -> pred(a.get()) }
  }
}

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <A : Argument<V>, V> ArgumentPredicate<*, *>.unsafeCast() =
  this as ArgumentPredicate<A, V>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun ArgumentPredicate<*, *>.forceAny() =
  this as ArgumentPredicate<Argument<Any?>, Any?>

internal object ArgSetFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ResolvedComponent<*, Any?>,
    config: CliSerializationConfig
  ) = argument.isSet
}

internal object ArgNullFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ResolvedComponent<*, Any?>,
    config: CliSerializationConfig,
  ) = argument.isSet && argument.get() != null
}

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
    reference: ResolvedComponent<*, Any?>,
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
        is Double        -> value == DoubleZero
        is Float         -> value == FloatZero
        is BigDecimal -> value == BigDecimal.ZERO
        is BigInteger -> value == BigInteger.ZERO
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

internal object ArgZeroFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ResolvedComponent<*, Any?>,
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

internal object ArgNegOneFilter : ArgumentPredicate<Argument<Any?>, Any?> {
  private inline val ByteNeg1: Byte get() = (-1).toByte()
  private inline val ShortNeg1: Short get() = (-1).toShort()
  private inline val IntNeg1: Int get() = -1
  private inline val LongNeg1: Long get() = -1L

  private inline val FloatNeg1: Float get() = -1.0F
  private inline val DoubleNeg1: Double get() = -1.0

  override fun shouldInclude(
    argument: Argument<Any?>,
    reference: ResolvedComponent<*, Any?>,
    config: CliSerializationConfig
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
      is Byte   -> value == ByteNeg1
      is Short  -> value == ShortNeg1

      is BigDecimal -> value == BigDecimal.ONE.negate()
      is BigInteger -> value == BigInteger.ONE.negate()

      else -> false
    }
}


private inline val ByteZero: Byte get() = 0.toByte()
private inline val ShortZero: Short get() = 0.toShort()
private inline val IntZero: Int get() = 0
private inline val LongZero: Long get() = 0L

private inline val FloatZero: Float get() = 0.0F
private inline val DoubleZero: Double get() = 0.0

private inline val UByteZero: UByte get() = 0.toUByte()
private inline val UShortZero: UShort get() = 0.toUShort()
private inline val UIntZero: UInt get() = 0u
private inline val ULongZero: ULong get() = 0uL

private inline val CharZero: Char get() = '\u0000'
