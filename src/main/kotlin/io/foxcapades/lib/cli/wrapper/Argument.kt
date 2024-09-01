package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliCallComponent
import kotlin.reflect.KProperty

/**
 * Represents a single positional or flag argument.
 */
interface Argument<T> : CliCallComponent {
  @get:Throws(UnsetArgumentDefaultException::class)
  val default: T

  val hasDefault: Boolean

  val isDefault: Boolean

  val isRequired get() = !hasDefault

  val isSet: Boolean

  @Throws(UnsetArgumentDefaultException::class)
  fun get(): T

  fun getOrNull() = if (isSet) get() else if (hasDefault) default else null

  fun getOrDefault(value: T) = getOrNull() ?: value

  @Throws(UnsetArgumentDefaultException::class)
  operator fun getValue(ref: Any?, property: KProperty<*>) = get()

  fun set(value: T)

  operator fun setValue(ref: Any?, property: KProperty<*>, value: T) = set(value)

  fun unset()
}

