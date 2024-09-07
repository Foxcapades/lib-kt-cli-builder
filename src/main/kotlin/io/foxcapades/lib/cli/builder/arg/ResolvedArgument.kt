package io.foxcapades.lib.cli.builder.arg

import io.foxcapades.lib.cli.builder.component.ResolvedComponent

/**
 * @param T Argument container class type.
 *
 * @param V Argument value type.
 */
interface ResolvedArgument<T : Any, V> : ResolvedComponent<T, V>, Argument<V>
