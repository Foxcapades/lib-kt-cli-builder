@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.lib.cli.builder.util.NoSuchValueException

@Suppress("UNCHECKED_CAST")
fun <T> Property(): Property<T> = EmptyProperty as Property<T>

private data object EmptyProperty : Property<Any?> {
  override val isSet get() = false
  override fun get() = throw NoSuchValueException()
}


fun <T> Property(value: T): Property<T> = ValueProperty(value)

@JvmInline
private value class ValueProperty<T>(private val value: T) : Property<T> {
  override val isSet get() = true
  override fun get() = value
}


inline fun <T> T.asProperty(): Property<T> = Property(this)


fun <T> Property<T>.toImmutable(): Property<T> =
  if (isSet) Property(get()) else Property()


inline fun <T> Property<T>.getOrNull() = if(isSet) get() else null

inline fun <T> Property<T>.getOr(fallback: T) = if (isSet) get() else fallback

inline fun <T> Property<T>.mapAbsent(crossinline other: () -> T) = if (isSet) this else Property(other())

inline fun <T> Property<T>.flatMapAbsent(crossinline other: () -> Property<T>) = if (isSet) this else other()

inline fun <T> Property<T>.getOrCompute(fn: () -> T): T {
  return if(isSet) get() else fn()
}

inline fun <T> Property<T>.getOrThrow(ex: Throwable): T =
  if (!isSet) throw ex else get()

inline fun <T> Property<T>.getOrThrow(fn: () -> Throwable) =
  if (!isSet) throw fn() else get()


@Suppress("UNCHECKED_CAST")
internal inline fun <T> Property<*>.unsafeCast() =
  this as Property<T>
