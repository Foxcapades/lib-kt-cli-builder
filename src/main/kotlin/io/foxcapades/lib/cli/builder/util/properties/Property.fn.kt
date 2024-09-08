@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

/**
 * Returns an empty [Property] instance.
 *
 * @param T Value type.
 *
 * @return An empty `Property` instance.
 */
@Suppress("UNCHECKED_CAST")
fun <T> Property(): Property<T> = EmptyProperty as Property<T>

private data object EmptyProperty : Property<Any?> {
  override val isSet get() = false
  override fun get() = throw NoSuchValueException()
}


/**
 * Returns a [Property] wrapping the given value.
 *
 * @param T Value Type.
 *
 * @param value Value to wrap.
 *
 * @return A `Property` wrapping the given value.
 */
fun <T> Property(value: T): Property<T> = ValueProperty(value)

@JvmInline
private value class ValueProperty<T>(private val value: T) : Property<T> {
  override val isSet get() = true
  override fun get() = value
}


/**
 * Wraps the receiver value as a property.
 */
inline fun <T> T.asProperty(): Property<T> = Property(this)


/**
 * Returns an immutable [Property] instance wrapping the value of the receiver.
 *
 * @receiver Property to re-wrap.
 *
 * @param T Value type.
 *
 * @return An immutable `Property` instance wrapping the value of the receiver
 * property.
 */
fun <T> Property<T>.toImmutable(): Property<T> =
  when (this) {
    is ValueProperty -> this
    is EmptyProperty -> this
    else             -> if (isSet) Property(get()) else Property()
  }


/**
 * Returns either the value wrapped by the receiver property, or `null` if the
 * property is not set.
 *
 * @receiver Receiver whose value should be retrieved, if set.
 *
 * @param T Value type.
 *
 * @return Either the wrapped `Property` value, or `null` if the property does
 * not contain a value.
 */
inline fun <T> Property<T>.getOrNull() = if(isSet) get() else null

/**
 * Returns either the value wrapped by the receiver property, or the given
 * [fallback] value if the property is not set.
 *
 * @receiver Receiver whose value should be retrieved, if set.
 *
 * @param T Value type.
 *
 * @return Either the wrapped `Property` value, or [fallback] if the property
 * does not contain a value.
 */
inline fun <T> Property<T>.getOr(fallback: T) = if (isSet) get() else fallback

inline fun <T> Property<T>.mapAbsent(crossinline other: () -> T) =
  if (isSet) this else Property(other())

inline fun <T> Property<T>.flatMapAbsent(crossinline other: () -> Property<T>) =
  if (isSet) this else other()

inline fun <T> Property<T>.getOrCompute(fn: () -> T): T =
  if(isSet) get() else fn()

inline fun <T> Property<T>.getOrThrow(ex: Throwable): T =
  if (!isSet) throw ex else get()

inline fun <T> Property<T>.getOrThrow(fn: () -> Throwable) =
  if (!isSet) throw fn() else get()


@Suppress("UNCHECKED_CAST")
internal inline fun <T> Property<*>.unsafeCast() =
  this as Property<T>
