package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

/**
 * # Argument Inclusion Predicate
 *
 * Defines a predicate function used to determine whether an [Argument] or its
 * parent should be included in CLI call serialization output based on the value
 * of that `Argument`.
 *
 * This predicate is only called on `Argument` instances that either have a
 * default value or have been explicitly set.
 *
 * A base implementation which simply includes the value if it is not equal to
 * a configured default:
 *
 * ```kt
 * predicate = { v, a, _, _ -> a.hasDefault && v == a.default }
 * ```
 *
 * @param V Type of value to be tested.
 *
 * @param A Argument type.
 *
 * @since 1.0.0
 */
fun interface ArgumentPredicate<A : Argument<V>, V> {
  /**
   * Called to test whether the given [Argument] should be included in the
   * serialized CLI call output.
   *
   * This function will only be called on `Argument` instances that either have
   * a default value or have been explicitly set.
   *
   * @param argument `Argument` instance containing the value under test.
   *
   * @param config Current serialization config.
   *
   * @param reference Reference to the class property or method from which the
   * `Argument` instance was obtained.  If the `Argument` instance is not a
   * property delegate, this parameter will be `null`.
   *
   * @return `true` if the value should be included in the serialized CLI call
   * output, otherwise `false`.
   */
  fun shouldInclude(
    argument:  A,
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?
  ): Boolean
}
