package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.flag.*
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.MutableProperty
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

// region Flag Base

/**
 * Represents a command line flag option.
 *
 * @since v1.0.0
 */
interface Flag<out A : Argument<V>, V> : CliCallComponent {
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
   * Method used to indicate whether a [Flag] instance should be included in
   * serialization based on customizable logic.
   *
   * Different implementations may provide varying default serialization
   * inclusion rules, however the default behavior provided by this interface is
   * to always include flags whose arguments return `true` on calls to
   * [Argument.shouldSerialize].
   *
   * `Flag` instances that are marked with [isRequired] = `true` will be always
   * be included in serialization.  For such instances, this method will not be
   * called.
   *
   * Implementers should indicate if/when they do not make use of a call to
   * [Argument.shouldSerialize].
   *
   * @param config Current serialization configuration.
   *
   * @return `true` if this `Flag` instance should be included in serialization
   * output, otherwise `false` if this `Flag` should be omitted.
   */
  fun shouldSerialize(config: CliSerializationConfig, reference: ResolvedFlag<*, V>): Boolean =
    argument.shouldSerialize(config, reference)

  fun writeToString(builder: CliAppender<*, V>)
}

/**
 * Convenience shortcut for `Flag.argument.isSet`.
 *
 * @see Flag.argument
 * @see Argument.isSet
 */
inline val Flag<*, *>.isSet get() = argument.isSet

/**
 * Convenience shortcut for `Flag.argument.hasDefault`.
 *
 * @see Flag.argument
 * @see Argument.hasDefault
 */
inline val Flag<*, *>.hasDefault get() = argument.hasDefault

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun Flag<*, *>.unsafeAnyType() =
  this as Flag<Argument<Any?>, Any?>

fun <V> CliAppender<*, V>.putPreferredFlagForm(flag: Flag<*, V>, withValue: Boolean) = apply {
  if (flag.hasLongForm) {
    if (config.preferredFlagForm.isLong || !flag.hasShortForm)
      putLongFlag(flag.longForm, withValue)
    else
      putShortFlag(flag.shortForm, withValue)
  } else if (flag.hasShortForm) {
    putShortFlag(flag.shortForm, withValue)
  } else {
    putLongFlag(config.propertyNameFormatter.format(reference.property.name, config), withValue)
  }
}

// endregion Flag Base

/**
 * @param T Flag container class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedFlag<T : Any, V> : ResolvedComponent<T, V>, Flag<Argument<V>, V>

// region Flag Options

sealed class BaseFlagOptions<V : Any, O : V?, A : BaseArgOptions<V, O>>(
  type: KClass<out V>,
  arg: A,
)
  : BaseComponentOptions<V>(type)
{
  /**
   * Sets the long-form name of the flag being configured.
   */
  var longForm by MutableProperty<String>()

  /**
   * Sets the short-form name of the flag being configured.
   */
  var shortForm by MutableProperty<Char>()

  /**
   * Sets whether this flag's presence is required in a CLI generated call.
   */
  var required by MutableProperty<Boolean>()

  /**
   * Defines a predicate which will is used to determine when a non-required
   * flag should be included or omitted from a CLI call.
   *
   * Flags marked as being [required] will always be included without any call
   * to this filter.
   */
  var flagFilter by MutableProperty<FlagPredicate<out Flag<out Argument<O>, O>, out Argument<O>, O>>()

  /**
   * Argument configuration
   */
  val argument: A = arg

  /**
   * Argument configuration action.
   *
   * Calls the given action on the value of [argument].
   */
  inline fun argument(crossinline action: A.() -> Unit) = argument.action()

  /**
   * Convenience shortcut to set `argument.required`.
   *
   * @see [BaseArgOptions.required]
   */
  inline var argRequired: Boolean
    get() = argument.required
    set(value) { argument.required = value }

  /**
   * Convenience shortcut to set `argument.filter`
   *
   * @see [BaseArgOptions.filter]
   */
  inline var argFilter: ArgumentPredicate<out Argument<O>, O>
    get() = argument.filter
    set(value) { argument.filter = value }

  /**
   * Convenience shortcut to set `argument.default`
   *
   * @see [BaseArgOptions.default]
   */
  inline var default: O
    get() = argument.default
    set(value) { argument.default = value }
}

open class FlagOptions<T: Any>(type: KClass<out T>)
  : BaseFlagOptions<T, T, ArgOptions<T>>(type, ArgOptions(type))

open class NullableFlagOptions<T: Any>(type: KClass<out T>)
  : BaseFlagOptions<T, T?, NullableArgOptions<T>>(type, NullableArgOptions(type))

// endregion Flag Options

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
