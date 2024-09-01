package io.foxcapades.lib.cli.wrapper.serial.properties

import io.foxcapades.lib.cli.wrapper.utils.toLowercase
import java.nio.CharBuffer

internal class LowerKebabCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length * 2

  override fun wordSeparator(buffer: CharBuffer) {
    buffer.put('-')
  }

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    for (c in word)
      buffer.put(c.toLowercase())
  }
}