package io.foxcapades.lib.cli.builder.util.values

import kotlin.reflect.KClass

internal object AnonymousComponentValue : ValueSource {
  override val name
    get() = "???"

  override val hasName: Boolean
    get() = false

  override val reference
    get() = "???::???"

  override val kind
    get() = ValueSource.Kind.Anonymous

  override val containerType: KClass<*>?
    get() = null

  override val instance: Any?
    get() = null
}
