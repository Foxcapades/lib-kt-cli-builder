package io.foxcapades.lib.cli.builder.serial

import io.foxcapades.lib.cli.builder.arg.Argument

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, V> CliArgumentWriter<T, V>.writeArgument(argument: Argument<V>) = argument.writeToString(this)
