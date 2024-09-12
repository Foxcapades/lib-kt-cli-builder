@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.impl.UniversalFlagImpl
import io.foxcapades.lib.cli.builder.util.BUG
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

// region Flag Base

@Deprecated("simplified type")
internal typealias AnyFlag = Flag<Any?>

@Suppress("UNCHECKED_CAST")
internal inline fun <V> Flag<*>.unsafeCast() = this as Flag<V>


internal inline fun Flag<*>.forceAny() = unsafeCast<Any?>()

internal inline val Flag<*>.safeName
  get() = if (hasLongForm) {
    "long-form($longForm)"
  } else if (hasShortForm) {
    "short-form($shortForm)"
  } else {
    BUG()
  }


// endregion Flag Base

// region Constructors

// region Constructors -> Non-null

// region Constructors -> Non-null -> Reified

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String by flag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by flag<String> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> flag(noinline action: FlagOptions<T>.() -> Unit = {}): Flag<T> =
  flag(T::class, action)

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String by flag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by flag<String> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param longForm Long-form name of the flag.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> flag(longForm: String, noinline action: FlagOptions<T>.() -> Unit = {}) =
  flag(longForm, T::class, action)

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String by flag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by flag<String> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param shortForm Short-form name of the flag.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> flag(shortForm: Char, noinline action: FlagOptions<T>.() -> Unit = {}) =
  flag(shortForm, T::class, action)

// endregion Constructors -> Non-null -> Reified

// region Constructors -> Non-null -> Concrete

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by flag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> flag(type: KClass<out T>, action: FlagOptions<T>.() -> Unit) =
  type.tryTyped(action) ?: UniversalFlagImpl.of(FlagOptions(type).also(action))

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by flag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param longForm Long-form name of the flag.
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> flag(longForm: String, type: KClass<out T>, action: FlagOptions<T>.() -> Unit) =
  flag(type) {
    this.longForm = longForm
    action()
  }

/**
 * Creates a new [Flag] delegate instance for the target type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by flag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor      |
 * |--------------|------------------|
 * | [BigDecimal] | [bigDecimalFlag] |
 * | [BigInteger] | [bigIntegerFlag] |
 * | [Boolean]    | [booleanFlag]    |
 * | [Byte]       | [byteFlag]       |
 * | [Char]       | [charFlag]       |
 * | [Double]     | [doubleFlag]     |
 * | [Float]      | [floatFlag]      |
 * | [Int]        | [intFlag]        |
 * | [Long]       | [longFlag]       |
 * | [Short]      | [shortFlag]      |
 * | [String]     | [stringFlag]     |
 * | [UByte]      | [ubyteFlag]      |
 * | [UInt]       | [uintFlag]       |
 * | [ULong]      | [ulongFlag]      |
 * | [UShort]     | [ushortFlag]     |
 *
 * @param shortForm Short-form name of the flag.
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> flag(shortForm: Char, type: KClass<out T>, action: FlagOptions<T>.() -> Unit) =
  flag(type) {
    this.shortForm = shortForm
    action()
  }

// endregion Constructors -> Non-null -> Concrete

// endregion Constructors -> Non-null

// region Constructors -> Nullable

// region Constructors -> Nullable -> Reified

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String? by nullableFlag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by nullableFlag<String?> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> nullableFlag(
  noinline action: NullableFlagOptions<T>.() -> Unit = {}
): Flag<T?> =
  nullableFlag(T::class, action)

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String? by nullableFlag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by nullableFlag<String?> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param longForm Long-form name of the flag.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> nullableFlag(
  longForm: String,
  noinline action: NullableFlagOptions<T>.() -> Unit = {}
): Flag<T?> =
  nullableFlag(longForm, T::class, action)

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input: String? by nullableFlag { longForm = "input" }
 *
 *   // registers `-o` string flag
 *   var output by nullableFlag<String?> { shortForm = 'o' }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param shortForm Short-form name of the flag.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
inline fun <reified T : Any> nullableFlag(
  shortForm: Char,
  noinline action: NullableFlagOptions<T>.() -> Unit = {}
): Flag<T?> =
  nullableFlag(shortForm, T::class, action)

// endregion Constructors -> Nullable -> Reified

// region Constructors -> Nullable -> Concrete

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by nullableFlag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> nullableFlag(type: KClass<out T>, action: NullableFlagOptions<T>.() -> Unit): Flag<T?> =
  type.tryNullableTyped(action) ?: UniversalFlagImpl.of(NullableFlagOptions(type).also(action))

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by nullableFlag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param longForm Long-form name of the flag.
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> nullableFlag(longForm: String, type: KClass<out T>, action: NullableFlagOptions<T>.() -> Unit): Flag<T?> =
  nullableFlag(type) {
    this.longForm = longForm
    action()
  }

/**
 * Creates a new [Flag] delegate instance for a nullable value of the target
 * type ([T]).
 *
 * ```kt
 * class SomeCommand {
 *   // registers `--input` string flag.
 *   var input by nullableFlag(String::class) { longForm = "input" }
 * }
 * ```
 *
 * @param T Flag value type.
 *
 * If `T` represents a Kotlin/Java built-in type, the flag creation will be
 * delegated to the appropriate type-specific flag constructor.
 *
 * | Type         | Constructor              |
 * |--------------|--------------------------|
 * | [BigDecimal] | [nullableBigDecimalFlag] |
 * | [BigInteger] | [nullableBigIntegerFlag] |
 * | [Boolean]    | [nullableBooleanFlag]    |
 * | [Byte]       | [nullableByteFlag]       |
 * | [Char]       | [nullableCharFlag]       |
 * | [Double]     | [nullableDoubleFlag]     |
 * | [Float]      | [nullableFloatFlag]      |
 * | [Int]        | [nullableIntFlag]        |
 * | [Long]       | [nullableLongFlag]       |
 * | [Short]      | [nullableShortFlag]      |
 * | [String]     | [nullableStringFlag]     |
 * | [UByte]      | [nullableUByteFlag]      |
 * | [UInt]       | [nullableUIntFlag]       |
 * | [ULong]      | [nullableULongFlag]      |
 * | [UShort]     | [nullableUShortFlag]     |
 *
 * @param shortForm Short-form name of the flag.
 *
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
fun <T : Any> nullableFlag(shortForm: Char, type: KClass<out T>, action: NullableFlagOptions<T>.() -> Unit): Flag<T?> =
  nullableFlag(type) {
    this.shortForm = shortForm
    action()
  }

// endregion Constructors -> Nullable -> Concrete

// endregion Constructors -> Nullable

// endregion Constructors

// region Constructor Support

private fun <T : Any> KClass<*>.tryTyped(action: FlagOptions<T>.() -> Unit): Flag<T>? {
  @Suppress("UNCHECKED_CAST")
  return when (java.`package`?.name) {

    null -> when (this) {
      Boolean::class -> booleanFlag { (action as FlagOptions<Boolean>.() -> Unit)(this) }
      Int::class     -> intFlag(action as FlagOptions<Int>.() -> Unit)
      Double::class  -> doubleFlag(action as FlagOptions<Double>.() -> Unit)
      Long::class    -> longFlag(action as FlagOptions<Long>.() -> Unit)
      Float::class   -> floatFlag(action as FlagOptions<Float>.() -> Unit)
      Byte::class    -> byteFlag(action as FlagOptions<Byte>.() -> Unit)
      Char::class    -> charFlag(action as FlagOptions<Char>.() -> Unit)
      Short::class   -> shortFlag(action as FlagOptions<Short>.() -> Unit)
      else           -> null
    }

    "java.lang" -> when (this) {
      String::class  -> stringFlag(action as FlagOptions<String>.() -> Unit)
      Boolean::class -> booleanFlag { (action as FlagOptions<Boolean>.() -> Unit)(this) }
      Int::class     -> intFlag(action as FlagOptions<Int>.() -> Unit)
      Double::class  -> doubleFlag(action as FlagOptions<Double>.() -> Unit)
      Long::class    -> longFlag(action as FlagOptions<Long>.() -> Unit)
      Float::class   -> floatFlag(action as FlagOptions<Float>.() -> Unit)
      Byte::class    -> byteFlag(action as FlagOptions<Byte>.() -> Unit)
      Char::class    -> charFlag(action as FlagOptions<Char>.() -> Unit)
      Short::class   -> shortFlag(action as FlagOptions<Short>.() -> Unit)
      else          -> null
    }

    "kotlin" -> when (this) {
      UInt::class   -> uintFlag(action as FlagOptions<UInt>.() -> Unit)
      ULong::class  -> ulongFlag(action as FlagOptions<ULong>.() -> Unit)
      UByte::class  -> ubyteFlag(action as FlagOptions<UByte>.() -> Unit)
      UShort::class -> ushortFlag(action as FlagOptions<UShort>.() -> Unit)
      else          -> null
    }

    "java.math" -> when (this) {
      BigInteger::class -> bigIntegerFlag(action as FlagOptions<BigInteger>.() -> Unit)
      BigDecimal::class -> bigDecimalFlag(action as FlagOptions<BigDecimal>.() -> Unit)
      else              -> null
    }

    else -> null
  } as Flag<T>?
}

private fun <T : Any> KClass<out T>.tryNullableTyped(action: NullableFlagOptions<T>.() -> Unit): Flag<T?>? {
  @Suppress("UNCHECKED_CAST")
  return when (java.`package`?.name) {

    null -> when (this) {
      Boolean::class -> nullableBooleanFlag { (action as NullableFlagOptions<Boolean>.() -> Unit)(this) }
      Int::class     -> nullableIntFlag(action as NullableFlagOptions<Int>.() -> Unit)
      Double::class  -> nullableDoubleFlag(action as NullableFlagOptions<Double>.() -> Unit)
      Long::class    -> nullableLongFlag(action as NullableFlagOptions<Long>.() -> Unit)
      Float::class   -> nullableFloatFlag(action as NullableFlagOptions<Float>.() -> Unit)
      Byte::class    -> nullableByteFlag(action as NullableFlagOptions<Byte>.() -> Unit)
      Char::class    -> nullableCharFlag(action as NullableFlagOptions<Char>.() -> Unit)
      Short::class   -> nullableShortFlag(action as NullableFlagOptions<Short>.() -> Unit)
      else           -> null
    }

    "java.lang" -> when (this) {
      String::class  -> nullableStringFlag(action as NullableFlagOptions<String>.() -> Unit)
      Boolean::class -> nullableBooleanFlag { (action as NullableFlagOptions<Boolean>.() -> Unit)(this) }
      Int::class     -> nullableIntFlag(action as NullableFlagOptions<Int>.() -> Unit)
      Double::class  -> nullableDoubleFlag(action as NullableFlagOptions<Double>.() -> Unit)
      Long::class    -> nullableLongFlag(action as NullableFlagOptions<Long>.() -> Unit)
      Float::class   -> nullableFloatFlag(action as NullableFlagOptions<Float>.() -> Unit)
      Byte::class    -> nullableByteFlag(action as NullableFlagOptions<Byte>.() -> Unit)
      Char::class    -> nullableCharFlag(action as NullableFlagOptions<Char>.() -> Unit)
      Short::class   -> nullableShortFlag(action as NullableFlagOptions<Short>.() -> Unit)
      else          -> null
    }

    "kotlin" -> when (this) {
      UInt::class   -> nullableUIntFlag(action as NullableFlagOptions<UInt>.() -> Unit)
      ULong::class  -> nullableULongFlag(action as NullableFlagOptions<ULong>.() -> Unit)
      UByte::class  -> nullableUByteFlag(action as NullableFlagOptions<UByte>.() -> Unit)
      UShort::class -> nullableUShortFlag(action as NullableFlagOptions<UShort>.() -> Unit)
      else          -> null
    }

    "java.math" -> when (this) {
      BigInteger::class -> nullableBigIntegerFlag(action as NullableFlagOptions<BigInteger>.() -> Unit)
      BigDecimal::class -> nullableBigDecimalFlag(action as NullableFlagOptions<BigDecimal>.() -> Unit)
      else              -> null
    }

    else -> null
  } as Flag<T?>?
}

// endregion Constructor Support
