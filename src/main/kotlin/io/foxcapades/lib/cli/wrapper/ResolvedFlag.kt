package io.foxcapades.lib.cli.wrapper

/**
 * @param T Flag container class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedFlag<T : Any, V> : ResolvedComponent<T, V>, Flag<Argument<V>, V>
