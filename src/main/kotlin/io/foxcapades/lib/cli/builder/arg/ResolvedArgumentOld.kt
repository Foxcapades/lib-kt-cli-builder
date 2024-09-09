package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.component.ResolvedComponentOld

/**
 * @param T Argument container class type.
 *
 * @param V Argument value type.
 */
interface ResolvedArgumentOld<T : Any, V> : ResolvedComponentOld<T, V>, Argument<V> {
  override val qualifiedName: String
    get() = "argument " + super.qualifiedName
}

