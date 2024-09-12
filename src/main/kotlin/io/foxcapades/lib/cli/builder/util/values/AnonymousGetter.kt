package io.foxcapades.lib.cli.builder.util.values

import kotlin.reflect.KClass

@JvmInline
internal value class AnonymousGetter<V>(val fn: () -> V) : ValueAccessor<V> {
  override val name
    get() = "???"

  override val hasName
    get() = false

  override val reference: String
    get() = "???::???"

  override val kind
    get() = ValueSource.Kind.Anonymous

  override val containerType: KClass<*>?
    get() = null

  override val instance: Any?
    get() = null

  override fun invoke() = fn()
}
