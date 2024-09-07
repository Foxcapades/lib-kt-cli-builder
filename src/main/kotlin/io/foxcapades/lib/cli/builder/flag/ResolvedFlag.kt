package io.foxcapades.lib.cli.builder.flag

import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.arg.Argument

/**
 * @param T Flag container class type.
 *
 * @param V Flag argument value type.
 */
interface ResolvedFlag<T : Any, V> : ResolvedComponent<T, V>, Flag<Argument<V>, V>
