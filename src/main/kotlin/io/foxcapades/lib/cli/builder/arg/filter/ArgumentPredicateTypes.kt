package io.foxcapades.lib.cli.builder.arg.filter

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Duration as JDuration
import java.util.Dictionary
import java.util.Optional
import java.util.OptionalDouble
import java.util.OptionalInt
import java.util.OptionalLong
import kotlin.reflect.KClass
import kotlin.time.Duration as KDuration

import io.foxcapades.lib.cli.builder.util.properties.Property

object ArgumentPredicateTypes {
  /**
   * Filters the target argument based on whether it has been explicitly set to
   * any value.
   */
  @JvmStatic
  val Set: KClass<out ArgumentPredicate<*>>
    get() = ArgUnsetFilter::class

  /**
   * Filters the target argument based on whether it is explicitly set to either
   * its configured default value or, if the value type is scalar, if the value
   * is equal to the default, or 'empty'/'zero' value for the type.
   *
   * Default type mappings:
   *
   * | Type                           | Exclude If |
   * |--------------------------------|------------|
   * | [Array]                        | is empty   |
   * | [BigDecimal]                   | `0.0`      |
   * | [BigInteger]                   | `0`        |
   * | [Boolean]                      | `false`    |
   * | [BooleanArray]                 | is empty   |
   * | [Byte]                         | `0`        |
   * | [ByteArray]                    | is empty   |
   * | [Char]                         | `0`        |
   * | [CharArray]                    | is empty   |
   * | [CharSequence]                 | is empty   |
   * | [Collection]                   | is empty   |
   * | [Dictionary]                   | is empty   |
   * | [Double]                       | `0.0`      |
   * | [DoubleArray]                  | is empty   |
   * | [Duration (Java)][JDuration]   | `0`        |
   * | [Duration (Kotlin)][KDuration] | `0`        |
   * | [Float]                        | `0.0`      |
   * | [FloatArray]                   | is empty   |
   * | [Int]                          | `0`        |
   * | [IntArray]                     | is empty   |
   * | [Long]                         | `0`        |
   * | [LongArray]                    | is empty   |
   * | [Map]                          | is empty   |
   * | [Optional]                     | is empty   |
   * | [OptionalDouble]               | is empty   |
   * | [OptionalInt]                  | is empty   |
   * | [OptionalLong]                 | is empty   |
   * | [Property]                     | is empty   |
   * | [Short]                        | `0`        |
   * | [ShortArray]                   | is empty   |
   * | [UByte]                        | `0`        |
   * | [UByteArray]                   | is empty   |
   * | [UInt]                         | `0`        |
   * | [UIntArray]                    | is empty   |
   * | [ULong]                        | `0`        |
   * | [ULongArray]                   | is empty   |
   * | [UShort]                       | `0`        |
   * | [UShortArray]                  | is empty   |
   * | [Any]?                         | `null`     |
   */
  @JvmStatic
  val Default: KClass<out ArgumentPredicate<*>>
    get() = ArgGeneralDefaultFilter::class

  /**
   * [ArgumentPredicate] implementation that excludes arguments that are unset,
   * or are set to `null` values.
   */
  @JvmStatic
  val Null: KClass<out ArgumentPredicate<*>>
    get() = ArgNullFilter::class

  /**
   * [ArgumentPredicate] implementation that excludes arguments that are unset,
   * or are set to `0`.
   *
   * As a special case, tests [Char] types for `null` char (code `0`).
   *
   * **WARNING** This test will always return `true` for any value type that is
   * not one of the following:
   *
   * * [Byte]
   * * [Short]
   * * [Int]
   * * [Long]
   * * [Float]
   * * [Double]
   * * [BigDecimal]
   * * [BigInteger]
   * * [UByte]
   * * [UShort]
   * * [UInt]
   * * [ULong]
   * * [Char]
   */
  @JvmStatic
  val Zero: KClass<out ArgumentPredicate<*>>
    get() = ArgZeroFilter::class

  /**
   * [ArgumentPredicate] implementation that excludes arguments that are unset,
   * or are set to `-1`
   *
   * **WARNING** This test will always return `true` for any value type that is
   * not one of the following:
   *
   * * [Byte]
   * * [Short]
   * * [Int]
   * * [Long]
   * * [Float]
   * * [Double]
   * * [BigDecimal]
   * * [BigInteger]
   */
  @JvmStatic
  val NegativeOne: KClass<out ArgumentPredicate<*>>
    get() = ArgNegOneFilter::class
}
