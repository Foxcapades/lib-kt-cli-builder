package io.foxcapades.lib.cli.wrapper.shell

import io.foxcapades.lib.cli.wrapper.Shell
import io.foxcapades.lib.cli.wrapper.util.CharacterAppender
import io.foxcapades.lib.cli.wrapper.util.isApostrophe
import io.foxcapades.lib.cli.wrapper.util.isPrintable

open class SH : Shell {
  override val name = "sh"

  override fun isFlagSafe(char: Char) = char.isPrintable

  override fun startString(appender: CharacterAppender) = appender.append('\'')

  override fun endString(appender: CharacterAppender) = appender.append('\'')

  override fun isArgumentSafe(char: Char) = !char.isApostrophe

  override fun escapeArgumentChar(char: Char, appender: CharacterAppender) {
    endString(appender)
    appender.append('"')
    appender.append(char)
    appender.append('"')
    startString(appender)
  }
}