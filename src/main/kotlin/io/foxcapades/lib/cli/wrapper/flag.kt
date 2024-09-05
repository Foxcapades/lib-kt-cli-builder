package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.flag.*
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.util.MutableProperty
import io.foxcapades.lib.cli.wrapper.util.SimpleProperty
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<A: Argument<V>, V> : MutableProperty<V>, CliCallComponent {
  /**
   * Indicates whether this flag has a long form.
   */
  val hasLongForm: Boolean

  /**
   * Long form of this flag.
   */
  val longForm: String

  /**
   * Indicates whether this flag has a short form.
   */
  val hasShortForm: Boolean

  /**
   * Short form of this flag.
   */
  val shortForm: Char

  /**
   * Argument for this flag.
   */
  val argument: A

  /**
   * Indicates whether this flag is required to be present in CLI calls.
   */
  val isRequired: Boolean

  /**
   * Indicates whether this flag has been set to a value.
   */
  override val isSet get() = argument.isSet

  /**
   * Indicates whether a default value has been set for this [Flag] instance's
   * underlying [Argument].
   *
   * If this value is `false`, attempting to access the `Flag`'s [default]
   * property will cause an [UnsetArgumentDefaultException] to be thrown.
   *
   * @see [Argument.hasDefault]
   */
  val hasDefault get() = argument.hasDefault

  /**
   * Tests whether this `Flag`'s underlying [Argument] is currently set to its
   * default value.
   *
   * See [Argument.isDefault] for additional information.
   *
   * @param config Serialization config.  Some implementations may require
   * converting this `Flag`'s underlying `Argument` value to a string in order
   * to test whether the value is the configured default.  In those cases,
   * serialization config info may be required to correctly serialize the value.
   *
   * @return `true` if this `Flag` has a default set, and the current value
   * of this `Flag` is equal to that default.
   *
   * @see Argument.isDefault
   */
  fun isDefault(config: CliSerializationConfig): Boolean =
    argument.isDefault(config)

  override fun get() = argument.get()

  override fun set(value: V) = argument.set(value)

  override fun unset() = argument.unset()

  fun writeToString(builder: CliAppender)
}

/**
 * @param T Flag container class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedFlag<T : Any, V> : ResolvedComponent<T, V>, Flag<Argument<V>, V>

sealed class BaseFlagOptions<T : Any, O : T?>(type: KClass<out T>)
  : BaseComponentOptions<T, O, ResolvedFlag<*, O>>(type)
{
  var longForm: String by SimpleProperty()
  var shortForm: Char by SimpleProperty()
  var requireFlag: Boolean by SimpleProperty()
}

open class FlagOptions<T: Any>(type: KClass<out T>) : BaseFlagOptions<T, T>(type)

open class NullableFlagOptions<T: Any>(type: KClass<out T>) : BaseFlagOptions<T, T?>(type)

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
@OptIn(ExperimentalContracts::class)
inline fun <reified T : Any> flag(noinline action: FlagOptions<T>.() -> Unit = {}): Flag<Argument<T>, T> {
  contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
  return flag(T::class, action)
}

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
@OptIn(ExperimentalContracts::class)
inline fun <reified T : Any> nullableFlag(
  noinline action: NullableFlagOptions<T>.() -> Unit = {}
): Flag<Argument<T?>, T?> {
  contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
  return nullableFlag(T::class, action)
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
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new [FlagOptions]
 * instance which will then be used to configure the newly created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
@OptIn(ExperimentalContracts::class)
fun <T : Any> flag(type: KClass<out T>, action: FlagOptions<T>.() -> Unit): Flag<Argument<T>, T> {
  contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
  return type.tryTyped(action) ?: GeneralFlagImpl.of(FlagOptions(type).also(action))
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
 * @param type Class for the type of value that the new `Flag` will hold.
 *
 * @param action Configuration that will be called on a new
 * [NullableFlagOptions] instance which will then be used to configure the newly
 * created `Flag`.
 *
 * @return New `Flag` instance configured by the given [action].
 */
@OptIn(ExperimentalContracts::class)
fun <T : Any> nullableFlag(type: KClass<out T>, action: NullableFlagOptions<T>.() -> Unit): Flag<Argument<T?>, T?> {
  contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
  return type.tryNullableTyped(action) ?: GeneralFlagImpl.of(NullableFlagOptions(type).also(action))
}

internal fun Flag<*, *>.safeName(config: CliSerializationConfig) =
  if (hasLongForm)
    config.longFlagPrefix + longForm
  else
    config.shortFlagPrefix + shortForm

@OptIn(ExperimentalContracts::class)
internal fun <T : Any> KClass<*>.tryTyped(action: FlagOptions<T>.() -> Unit): Flag<Argument<T>, T>? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }

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
  } as Flag<Argument<T>, T>?
}

@OptIn(ExperimentalContracts::class)
internal fun <T : Any> KClass<out T>.tryNullableTyped(action: NullableFlagOptions<T>.() -> Unit): Flag<Argument<T?>, T?>? {
  contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }

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
  } as Flag<Argument<T?>, T?>?
}
