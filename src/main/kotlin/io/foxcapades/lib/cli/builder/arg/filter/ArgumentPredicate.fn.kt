@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import kotlin.reflect.KClass

/**
 * Convenience method for defining a simple, value-based [ArgumentPredicate]
 * instance.
 *
 * Example:
 * ```kt
 * // Simple predicate that only includes arguments whose value equals `true`.
 * ArgumentPredicate.simple { it == true }
 * ```
 *
 * @param V Type of value to be tested.
 *
 * @param A Argument type.
 *
 * @param pred Actual predicate implementation that will be passed a target
 * value to determine whether its containing argument should be included in the
 * serialized CLI call.
 *
 * @return An `ArgumentPredicate` instance wrapping the given [predicate][pred].
 */
inline fun <V> ArgumentPredicate(crossinline pred: (V) -> Boolean) =
  ArgumentPredicate { a, _, _ -> pred(a.get()) }


//


@Suppress("UNCHECKED_CAST")
internal inline fun <V> ArgumentPredicate<*>.unsafeCast() = this as ArgumentPredicate<V>
