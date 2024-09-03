package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.reflect.PropertyReference

/**
 * @param T Component container class type.
 *
 * @param V Component value type.
 */
sealed interface ResolvedComponent<T : Any, V> : CliCallComponent, PropertyReference<T, V>
