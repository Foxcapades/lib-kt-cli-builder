package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.util.CharacterAppender

interface Shell {
  val name: String

  val usePostProcessing: Boolean
    get() = false

  fun isFlagSafe(char: Char): Boolean

  fun startString(appender: CharacterAppender)

  fun endString(appender: CharacterAppender)

  fun isArgumentSafe(char: Char, inQuotes: Boolean): Boolean

  fun escapeArgumentChar(char: Char, inQuotes: Boolean, appender: CharacterAppender)

  fun postProcessArgumentValue(value: String, appender: CharacterAppender) {}
}
