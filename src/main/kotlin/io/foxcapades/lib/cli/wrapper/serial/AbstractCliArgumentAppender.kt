package io.foxcapades.lib.cli.wrapper.serial

internal abstract class AbstractCliArgumentAppender : CliArgumentAppender {
  protected abstract val buffer: StringBuilder

  var forString = false

  override fun appendChar(char: Char): CliArgumentAppender {
    if (forString) {
      if (config.targetShell.isArgumentSafe(char)) {
        buffer.append(char)
      } else {
        config.targetShell.escapeArgumentChar(char, buffer::append)
      }
    } else {
      buffer.append(char)
    }

    return this
  }

  override fun appendChar(char: Byte) = appendChar(char.toInt().toChar())

  override fun appendString(string: CharSequence) = also { string.forEach(::appendChar) }
}
