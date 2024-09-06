package io.foxcapades.lib.cli.wrapper.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.reflect.KProperty

// region Property Def

/**
 * Represents a generic value which may or may not exist.
 *
 * Similar to Java's [java.util.Optional] type, but allowing for class property
 * delegation.
 *
 * @param T Type of value this `Property` may contain.
 *
 * @since 1.0.0
 */
interface Property<T> {
  /**
   * Indicates whether this [Property] currently contains a value.
   *
   * If `isSet` is `false`, then calling [get] will result in an exception being
   * thrown.
   */
  val isSet: Boolean

  /**
   * Returns the value contained in this [Property] instance, if one is set.
   *
   * If no value is currently set on this `Property`, this method will throw an
   * exception.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchValueException If this method is called when the property has
   * no value set.
   */
  @Throws(NoSuchValueException::class)
  fun get(): T

  /**
   * Enables the use of [Property] instances as read-only class property
   * delegates (`val`).
   *
   * Specific implementations may alter the behavior of this method, however the
   * default implementation simply calls [get].
   *
   * If no value is currently set on this `Property`, this method will throw an
   * exception.
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchValueException If this method is called when the property has
   * no value set.
   */
  @Throws(NoSuchValueException::class)
  operator fun getValue(thisRef: Any?, property: KProperty<*>) = get()

  companion object {
    /**
     * Returns an empty [Property] instance for the specified type parameter
     * ([T]).
     *
     * @param T Type of value the returned empty property might have contained.
     *
     * @return An empty [Property] instance.
     */
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> empty(): Property<T> = EmptyProperty as Property<T>
  }
}

open class NoSuchValueException(msg: String) : RuntimeException(msg) {
  constructor() : this("attempted to unwrap a value that had not been set")
}

fun <T> Property(): Property<T> = Property.empty()

fun <T> Property(value: T): Property<T> = ValueProperty(value)

fun <T> Property<T>.toImmutable(): Property<T> =
  if (isSet) Property(get()) else Property()

@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
internal inline fun <T> Property<*>.unsafeCast() =
  this as Property<T>

@JvmInline
private value class ValueProperty<T>(private val value: T) : Property<T> {
  override val isSet
    get() = true

  override fun get() = value
}

private data object EmptyProperty : Property<Any?> {
  override val isSet: Boolean
    get() = false

  override fun get(): Any? {
    throw NoSuchValueException()
  }
}

// endregion Property Def

// region Mutable Property Def

/**
 * Represents a replaceable/settable generic value which may or may not exist.
 *
 * @param T Type of value this `MutableProperty` may contain.
 *
 * @since 1.0.0
 */
interface MutableProperty<T> : Property<T> {
  /**
   * Sets the value wrapped by this `MutableProperty` to the value provided.
   *
   * If this `MutableProperty` was already set, the provided value will replace
   * the previous one.
   *
   * @param value Value to set for this `MutableProperty`.
   */
  fun set(value: T)

  /**
   * Enables the use of [MutableProperty] instances as mutable class property
   * delegates (`var`).
   *
   * If this `MutableProperty` was already set, the provided value will replace
   * the previous one.
   *
   * Specific implementations may alter the behavior of this method, however the
   * default implementation simply calls [set].
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @param value Value to set for this `MutableProperty`.
   */
  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = set(value)

  /**
   * Removes any value previously set on this `MutableProperty` instance.
   *
   * If this `MutableProperty` instance was already unset, this method does
   * nothing.
   *
   * After this method returns, [isSet] will return `false`, and calls to [get]
   * will throw an exception until a new value has been [set].
   */
  fun unset()
}

fun <T> MutableProperty(): MutableProperty<T> = SimpleMutableProperty()

fun <T> MutableProperty(value: T): MutableProperty<T> = SimpleMutableProperty(value)

private class SimpleMutableProperty<T> : MutableProperty<T> {
  override var isSet: Boolean
    private set

  private var value: T?

  constructor() {
    this.value = null
    this.isSet = false
  }

  constructor(value: T) {
    this.value = value
    this.isSet = true
  }

  @Suppress("UNCHECKED_CAST")
  override fun get() =
    if (isSet)
      value as T
    else
      throw NoSuchValueException()

  override fun set(value: T) {
    this.value = value
    this.isSet = true
  }

  override fun unset() {
    this.value = null
    this.isSet = false
  }
}

// endregion Mutable Property Def

// region Defaultable Property Def

/**
 * Represents a generic value which may or may not be set along with a fallback
 * default value which also may or may not be set.
 *
 * @param T Type of value this `DefaultableProperty` may contain.
 *
 * @since 1.0.0
 */
interface DefaultableProperty<T> : Property<T> {
  /**
   * Indicates whether this `DefaultableProperty` has a default value set.
   *
   * If `hasDefault` is `false`, calls to [getDefault] will result in an
   * exception being thrown.
   */
  val hasDefault: Boolean

  /**
   * Returns the default fallback value contained in this [DefaultableProperty]
   * instance, if one is set.
   *
   * If no default value is currently set on this `DefaultableProperty`, this
   * method will throw an exception.
   *
   * @return The value set for this property.
   *
   * @throws NoSuchDefaultValueException If this method is called when the
   * property has no default set.
   */
  @Throws(NoSuchDefaultValueException::class)
  fun getDefault(): T

  /**
   * Returns either the current value of this `DefaultableProperty`, if one is
   * set, or the default value, if one is set.
   *
   * If neither a value nor a default value have been set on this
   * `DefaultableProperty`, this method will throw an exception.
   *
   * @return Either the value of this property, or its default fallback value.
   *
   * @throws NoSuchValueException If neither a value nor default value are set
   * on this `DefaultableProperty`.
   */
  @Throws(NoSuchValueException::class)
  fun getOrDefault(): T = if (!isSet && hasDefault) getDefault() else get()

  /**
   * Overrides the parent implementation [Property.getValue] with a default that
   * calls [getOrDefault] instead of [get].
   *
   * Specific implementations may alter the behavior of this method, but are
   * expected to adhere to the rules defined by [getOrDefault].
   *
   * If neither a value nor a default value have been set on this
   * `DefaultableProperty`, this method will throw an exception.
   *
   * @param thisRef Reference to the instance containing the delegated property
   * that was accessed.
   *
   * @param property Reference to the class property that delegated access to
   * this `Property` instance.
   *
   * @return Either the value of this property, or its default fallback value.
   *
   * @throws NoSuchValueException If neither a value nor default value are set
   * on this `DefaultableProperty`.
   */
  @Throws(NoSuchValueException::class)
  override operator fun getValue(thisRef: Any?, property: KProperty<*>) = getOrDefault()
}


/**
 * # No Such Default Value
 *
 * Exception thrown by [DefaultableProperty] types when an attempt is made to
 * access a default value that does not exist.
 */
open class NoSuchDefaultValueException(msg: String) : NoSuchValueException(msg) {
  constructor() : this("attempted to unwrap a default value that had not been set")
}


/**
 * Creates a new, empty, non-defaulting [DefaultableProperty] instance.
 *
 * @param T Type of the value that might have been contained by the returned
 * `DefaultableProperty`.
 *
 * @return A new `DefaultableProperty` instance with no value or default value
 * set.
 */
fun <T> DefaultableProperty(): DefaultableProperty<T> = BasicDefaultableProperty(0, null, null)

fun <T> NonDefaultedProperty(value: T): DefaultableProperty<T> = BasicDefaultableProperty(1, null, value)

fun <T> DefaultedProperty(default: T): DefaultableProperty<T> = BasicDefaultableProperty(2, default, null)

fun <T> DefaultedProperty(default: T, value: T): DefaultableProperty<T> = BasicDefaultableProperty(3, default, value)

internal open class BasicDefaultableProperty<T>(state: Byte, default: T?, value: T?) : DefaultableProperty<T> {
  // 0 = blank
  // 1 = has value
  // 2 = has default
  // 3 = has value and default
  private var state = state

  private val default = default

  private val value = value

  override val isSet: Boolean
    get() = Bytes.equal(state and 1, 1)

  override val hasDefault: Boolean
    get() = Bytes.equal(state and 2, 2)

  @Suppress("UNCHECKED_CAST")
  override fun getDefault() =
    if (hasDefault) default as T else throw NoSuchDefaultValueException()

  @Suppress("UNCHECKED_CAST")
  override fun get() =
    if (isSet) value as T else throw NoSuchValueException()
}

// endregion Defaultable Property Def

// region Mutable Defaultable Property Def

interface MutableDefaultableProperty<T> : DefaultableProperty<T>, MutableProperty<T>

fun <T> MutableDefaultableProperty(): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(0, null, null)

fun <T> MutableNonDefaultedProperty(value: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(1, null, value)

fun <T> MutableDefaultedProperty(default: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(2, default, null)

fun <T> MutableDefaultableProperty(default: T, value: T): MutableDefaultableProperty<T> =
  BasicMutableDefaultableProperty(3, default, value)

internal open class BasicMutableDefaultableProperty<T>(state: Byte, default: T?, value: T?)
  : MutableDefaultableProperty<T>
{
  // 0 = blank
  // 1 = has value
  // 2 = has default
  // 3 = has value and default
  private var state = state

  private val default = default

  private var value = value

  override val isSet: Boolean
    get() = Bytes.equal(state and 1, 1)

  override val hasDefault: Boolean
    get() = Bytes.equal(state and 2, 2)

  @Suppress("UNCHECKED_CAST")
  override fun getDefault() =
    if (hasDefault) default as T else throw NoSuchDefaultValueException()

  @Suppress("UNCHECKED_CAST")
  override fun get() =
    if (isSet) value as T else throw NoSuchValueException()

  override fun set(value: T) {
    this.value = value
    this.state = state or 1
  }

  override fun unset() {
    this.value = null
    this.state = state and Bytes.One.inv()
  }
}

// endregion Mutable Defaultable Property Def

@JvmInline
internal value class BooleanProperty private constructor(private val value: Boolean) : Property<Boolean> {
  override val isSet get() = true
  override fun get() = value

  companion object {
    @JvmStatic
    val True = BooleanProperty(true)

    @JvmStatic
    val False = BooleanProperty(false)
  }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> T.asProperty(): Property<T> = Property(this)

// TODO: rename this
private class SimpleProperty<T> : MutableProperty<T> {
  private var value: T? = null

  constructor()

  constructor(value: T) {
    set(value)
  }

  override var isSet = false
    private set

  @Suppress("UNCHECKED_CAST")
  override fun get() = if (!isSet) throw NoSuchValueException() else value as T

  override fun set(value: T) {
    this.value = value
    this.isSet = true
  }

  override fun unset() {
    value = null
    isSet = false
  }

  @Suppress("UNCHECKED_CAST")
  inline fun <R> map(fn: (T) -> R): SimpleProperty<R> =
    getOrNull()
      ?.let(fn)
      ?.let(::SimpleProperty)
      ?.apply { isSet = true } as SimpleProperty<R>?
      ?: SimpleProperty()
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Property<T>.getOrNull() = if(isSet) get() else null

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Property<T>.getOr(fallback: T) = if (isSet) get() else fallback

@OptIn(ExperimentalContracts::class)
inline fun <T> Property<T>.getOrCompute(fn: () -> T): T {
  contract { callsInPlace(fn, InvocationKind.AT_MOST_ONCE) }

  return if(isSet) get() else fn()
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Property<T>.getOrThrow(ex: Throwable): T =
  if (!isSet)
    throw ex
  else
    get()

@OptIn(ExperimentalContracts::class)
inline fun <T> Property<T>.getOrThrow(fn: () -> Throwable): T {
  contract { callsInPlace(fn, InvocationKind.AT_MOST_ONCE) }

  if (!isSet)
    throw fn()

  return get()
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> MutableProperty<T>.setIfAbsent(value: T) =
  also { if (!isSet) set(value) }

@OptIn(ExperimentalContracts::class)
inline fun <T> MutableProperty<T>.computeIfAbsent(fn: () -> T): MutableProperty<T> {
  contract { callsInPlace(fn, InvocationKind.AT_MOST_ONCE) }

  if (!isSet)
    set(fn())

  return this
}
