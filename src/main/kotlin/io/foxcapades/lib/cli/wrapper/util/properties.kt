package io.foxcapades.lib.cli.wrapper.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KProperty

interface Property<T> {
  val isSet: Boolean

  fun get(): T

  operator fun getValue(thisRef: Any?, property: KProperty<*>) = get()

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> empty(): Property<T> = EmptyProperty as Property<T>
  }
}

interface MutableProperty<T> : Property<T> {
  fun set(value: T)

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = set(value)

  fun unset()
}

@JvmInline
value class ValueProperty<T>(private val value: T) : Property<T> {
  override val isSet
    get() = true

  override fun get() = value
}

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
inline fun <T> T.asProperty(): Property<T> = ValueProperty(this)

// TODO: rename this
internal class SimpleProperty<T> : MutableProperty<T> {
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

private data object EmptyProperty : Property<Any?> {
  override val isSet: Boolean
    get() = false

  override fun get(): Any? {
    throw NoSuchValueException()
  }
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
