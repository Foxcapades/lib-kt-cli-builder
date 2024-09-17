@file:Suppress("NOTHING_TO_INLINE")
package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument

/**
 * Convenience method for defining a simple, value-based [ArgumentPredicate]
 * instance.
 *
 * `ArgumentPredicate` instances created via this method WILL NOT be called for
 * arguments that are not set.  If [Argument.isSet] returns false, the returned
 * predicate will also return false.
 *
 * Example:
 * ```kt
 * // Simple predicate that only includes arguments whose value equals `true`.
 * ArgumentPredicate.simple { it == true }
 * ```
 *
 * @param V Type of value to be tested.
 *
 * @param pred Actual predicate implementation that will be passed a target
 * value to determine whether its containing argument should be included in the
 * serialized CLI call.
 *
 * @return An `ArgumentPredicate` instance wrapping the given [predicate][pred].
 */
inline fun <V> ArgumentPredicate(crossinline pred: (V) -> Boolean) =
  ArgumentPredicate { a, _, _ -> a.isSet && pred(a.get()) }


//


@Suppress("UNCHECKED_CAST")
internal inline fun <V> ArgumentPredicate<*>.unsafeCast() = this as ArgumentPredicate<V>
