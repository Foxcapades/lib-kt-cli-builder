package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.kt.prop.delegation.MutableDefaultableDelegateProperty
import io.foxcapades.lib.cli.builder.UnsetArgumentDefaultException

interface DelegateArgument<V> : Argument<V>, MutableDefaultableDelegateProperty<V, V?> {
  val isDefault: Boolean
    get() = hasDefault && isSet && getDefault() == get()

  @Throws(UnsetArgumentDefaultException::class)
  override fun getDefault(): V
}
