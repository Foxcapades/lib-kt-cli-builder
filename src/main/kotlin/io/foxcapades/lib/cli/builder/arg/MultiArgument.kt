package io.foxcapades.lib.cli.builder.arg

/**
 * Argument type intended as a base type for arguments that contain multiple
 * values.
 *
 * @since 1.0.0
 */
interface MultiArgument<I : Iterable<T>, T> : ComplexArgument<I>
