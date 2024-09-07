package io.foxcapades.lib.cli.builder.arg

/**
 * Argument type specifically intended for scalar values as opposed to
 * [ComplexArgument].
 *
 * @param T Value type for this argument.
 *
 * @since 1.0.0
 */
interface ScalarArgument<T> : Argument<T>
