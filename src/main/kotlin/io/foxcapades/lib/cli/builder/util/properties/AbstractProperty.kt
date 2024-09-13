package io.foxcapades.lib.cli.builder.util.properties

import io.foxcapades.kt.prop.delegation.MutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.Bytes
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

internal abstract class AbstractProperty<T>(
  state:   Byte,
  default: T?,
  value:   T?
)
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
  override fun get() =
    if (isSet)
      value as T
    else
      throw io.foxcapades.kt.prop.delegation.NoSuchValueException()

  @Suppress("UNCHECKED_CAST")
  override fun getDefault() =
    if (hasDefault)
      default as T
    else
      throw io.foxcapades.kt.prop.delegation.NoSuchValueException()

  override fun set(value: T) {
    this.value = value
    this.state = state or 1
  }

  override fun unset() {
    this.value = null
    this.state = state and Bytes.One.inv()
  }
}
