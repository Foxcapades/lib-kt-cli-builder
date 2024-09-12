package io.foxcapades.lib.cli.builder.arg.filter

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

/**
 * # Argument Inclusion Predicate
 *
 * Defines a predicate function used to determine whether an [Argument] should
 * be included in CLI call serialization output based on the value of that
 * `Argument`.
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
 * @since 1.0.0
 */
fun interface ArgumentPredicate<V> {
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
   * @param source Basic information about the source of the argument value.
   * An `Argument` source would typically be a class property or getter method.
   *
   * @return `true` if the value should be included in the serialized CLI call
   * output, otherwise `false`.
   */
  fun shouldInclude(argument: Argument<V>, config: CliSerializationConfig, source: ValueSource): Boolean
}
