package io.foxcapades.lib.cli.builder.flag.properties

import io.foxcapades.lib.cli.builder.util.toUppercase
import java.nio.CharBuffer

internal class UpperKebabCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length * 2

  override fun wordSeparator(buffer: CharBuffer) {
    buffer.put('-')
  }

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    for (c in word)
      buffer.put(c.toUppercase())
  }
}
