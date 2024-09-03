package io.foxcapades.lib.cli.wrapper

/**
 * @param T Argument container class type.
 *
 * @param V Argument value type.
 */
interface ResolvedArgument<T : Any, V> : ResolvedComponent<T, V>, Argument<V>
