@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.flag.impl.UniversalDelegateFlag
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

// region Flag Base

@Suppress("UNCHECKED_CAST")
internal inline fun <V> Flag<*>.unsafeCast() = this as Flag<V>


internal inline fun Flag<*>.forceAny() = unsafeCast<Any?>()

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
inline fun <reified T : Any> flag(noinline action: FlagOptions<T>.() -> Unit = {}): DelegateFlag<T> =
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
  type.tryTyped(action) ?: UniversalDelegateFlag.of(FlagOptions(type).also(action))

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

// endregion Constructors

// region Constructor Support

private fun <T : Any> KClass<*>.tryTyped(action: FlagOptions<T>.() -> Unit): DelegateFlag<T>? {
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
  } as DelegateFlag<T>?
}

// endregion Constructor Support
