package io.foxcapades.lib.cli.builder.arg

/**
 * Argument type specifically intended for complex/compound values as opposed to
 * [ScalarArgument].
 *
 * @param T Value type for this argument.
 *
 * @since 1.0.0
 */
interface ComplexArgument<T> : Argument<T>
