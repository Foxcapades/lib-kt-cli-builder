package io.foxcapades.lib.cli.wrapper.serial

import io.foxcapades.lib.cli.wrapper.Argument

interface CliAppender {
  val config: CliSerializationConfig

  fun putLongFlag(name: String, hasValue: Boolean): CliAppender

  fun putShortFlag(name: Char, hasValue: Boolean): CliAppender

  fun putArgument(argument: Argument<*>): CliAppender
}

