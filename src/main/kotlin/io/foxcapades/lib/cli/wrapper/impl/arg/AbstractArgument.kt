package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.UnsetArgumentDefaultException

internal sealed class AbstractArgument<T>(override val hasDefault: Boolean) : Argument<T> {
  final override var isSet = false
    private set

  override val isDefault
    get() = hasDefault && (isSet && safeGet() == default || !isSet)

  override fun get() =
    if (isSet) safeGet()
    else if (hasDefault) default
    else throw UnsetArgumentDefaultException()

  override fun set(value: T) {
    safeSet(value)
    isSet = true
  }

  override fun unset() {
    safeUnset()
    isSet = false
  }

  protected abstract fun safeGet(): T

  protected abstract fun safeSet(value: T)

  protected abstract fun safeUnset()
}