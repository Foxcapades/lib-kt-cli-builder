package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Argument

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, V> CliArgumentWriter<T, V>.writeArgument(argument: Argument<V>) = argument.writeToString(this)
