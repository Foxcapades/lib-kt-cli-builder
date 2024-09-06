package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.ResolvedComponent

/**
 * @param T Command config class type.
 *
 * @param V Value type.
 */
interface CliAppender<T : Any, V> {
  val config: CliSerializationConfig

  val reference: ResolvedComponent<T, V>

  fun putLongFlag(name: String, hasValue: Boolean): CliAppender<T, V>

  fun putShortFlag(name: Char, hasValue: Boolean): CliAppender<T, V>

  fun putArgument(argument: Argument<*>): CliAppender<T, V>
}

