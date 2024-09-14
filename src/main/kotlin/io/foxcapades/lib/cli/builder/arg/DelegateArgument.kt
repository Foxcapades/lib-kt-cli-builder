package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.kt.prop.delegation.MutableDefaultableDelegateProperty

interface DelegateArgument<V> : Argument<V>, MutableDefaultableDelegateProperty<V, V?> {
  val isDefault: Boolean
    get() = hasDefault && isSet && getDefault() == get()

  override fun getDefault(): V
}
