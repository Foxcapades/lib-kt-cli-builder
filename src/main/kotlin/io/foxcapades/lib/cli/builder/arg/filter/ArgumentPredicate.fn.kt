package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument

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
inline fun <A : Argument<V>, V> ArgumentPredicate(crossinline pred: (V) -> Boolean) =
  ArgumentPredicate<A, V> { a, _, _ -> pred(a.get()) }

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun <A : Argument<V>, V> ArgumentPredicate<*, *>.unsafeCast() =
  this as ArgumentPredicate<A, V>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
internal inline fun ArgumentPredicate<*, *>.forceAny() =
  this as ArgumentPredicate<Argument<Any?>, Any?>
