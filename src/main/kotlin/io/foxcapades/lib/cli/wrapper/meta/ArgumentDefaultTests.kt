package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.serial.values.*
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

object ArgumentDefaultTests {
  /**
   * Tests whether the annotated property value is the default for one of the
   * known built-in types.
   *
   * Default type mappings:
   *
   * | Type           | Condition | Notes                                     |
   * |----------------|-----------|-------------------------------------------|
   * | [Array]        | is empty  | All non-primitive arrays.                 |
   * | [BigDecimal]   | `0.0`     |
   * | [BigInteger]   | `0`       |
   * | [Boolean]      | `false`   |
   * | [BooleanArray] | is empty  |
   * | [Byte]         | `0`       |
   * | [ByteArray]    | is empty  |
   * | [Char]         | `0`       |
   * | [CharArray]    | is empty  |
   * | [CharSequence] | is empty  | Catches [String], [StringBuilder], etc... |
   * | [Collection]   | is empty  | Catches [UByteArray], [UIntArray], etc... |
   * | [Double]       | `0.0`     |
   * | [DoubleArray]  | is empty  |
   * | [Float]        | `0.0`     |
   * | [FloatArray]   | is empty  |
   * | [Int]          | `0`       |
   * | [IntArray]     | is empty  |
   * | [Long]         | `0`       |
   * | [LongArray]    | is empty  |
   * | [Map]          | is empty  |
   * | [Short]        | `0`       |
   * | [ShortArray]   | is empty  |
   * | [UByte]        | `0`       |
   * | [UInt]         | `0`       |
   * | [ULong]        | `0`       |
   * | [UShort]       | `0`       |
   * | [Any]?         | `null`    | Fallthrough                               |
   */
  @JvmStatic
  val Default: KClass<out ArgumentDefaultTest<*>>
    get() = ArgGeneralDefaultTest::class

  /**
   * [ArgumentDefaultTest] implementation that always returns `false`.
   */
  @JvmStatic
  val None: KClass<out ArgumentDefaultTest<*>>
    get() = ArgAlwaysFalseDefaultTest::class

  /**
   * [ArgumentDefaultTest] implementation that only considers `null` values to
   * be default.
   */
  @JvmStatic
  val Null: KClass<out ArgumentDefaultTest<*>>
    get() = ArgNullDefaultTest::class

  /**
   * [ArgumentDefaultTest] implementation that only considers numeric zero
   * values to be defaults.
   *
   * As a special case, tests [Char] types for `null` char (code `0`).
   *
   * This test will return `false` for any type that is not one of the
   * following:
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
  val Zero: KClass<out ArgumentDefaultTest<*>>
    get() = ArgZeroDefaultTest::class

  /**
   * [ArgumentDefaultTest] implementation that only considers numeric values
   * equal to `-1` to be defaults.
   *
   * This test will return `false` for any type that is not one of the
   * following:
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
  val NegativeOne: KClass<out ArgumentDefaultTest<*>>
    get() = ArgNegativeOneDefaultTest::class
}
