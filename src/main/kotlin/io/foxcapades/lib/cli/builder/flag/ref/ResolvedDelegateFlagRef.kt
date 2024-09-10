package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

/**
 * Represents a reference to a [Flag] instance that is used as a property
 * delegate on its parent command config.
 *
 * @param T Parent/containing class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedDelegateFlagRef<T : Any, V> : ResolvedFlag<V>, ValueAccessorReference<T, V, KCallable<V>> {
  /**
   * Parent command definition class.
   */
  override val parentComponent: ResolvedCommand<T>

  /**
   * [KClass] of the containing parent command definition.
   */
  override val containingType: KClass<out T>
    get() = parentComponent.type

  override val qualifiedName: String
    get() = "flag " + super.qualifiedName
}
