package io.foxcapades.lib.cli.wrapper.serial.properties

import io.foxcapades.lib.cli.wrapper.utils.toLowercase
import io.foxcapades.lib.cli.wrapper.utils.toUppercase
import java.nio.CharBuffer

internal class TitleKebabCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length * 2

  override fun wordSeparator(buffer: CharBuffer) {
    buffer.put('-')
  }

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    buffer.put(word[0].toUppercase())
    for (i in 1 ..< word.length)
      buffer.put(word[i].toLowercase())
  }
}