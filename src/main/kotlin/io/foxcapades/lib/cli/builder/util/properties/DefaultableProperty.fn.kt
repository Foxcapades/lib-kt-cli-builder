@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.lib.cli.builder.util.Bytes
import io.foxcapades.lib.cli.builder.util.NoSuchDefaultValueException
import io.foxcapades.lib.cli.builder.util.NoSuchValueException
import kotlin.experimental.and


// region Defaultable Property Def

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

private class BasicDefaultableProperty<T>(state: Byte, default: T?, value: T?) : DefaultableProperty<T> {
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
