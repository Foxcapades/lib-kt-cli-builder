package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.component.ResolvedComponentOld

/**
 * @param T Flag container class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedFlagOld<T : Any, V> : ResolvedComponentOld<T, V>, Flag<Argument<V>, V> {
  override val qualifiedName: String
    get() = "flag " + super.qualifiedName
}

