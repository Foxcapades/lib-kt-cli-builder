package io.foxcapades.lib.cli.builder.flag.properties

import io.foxcapades.lib.cli.builder.util.toLowercase
import io.foxcapades.lib.cli.builder.util.toUppercase
import java.nio.CharBuffer

internal class CamelCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length

  override fun firstWord(word: String, buffer: CharBuffer) {
    for (c in word)
      buffer.put(c.toLowercase())
  }

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    buffer.put(word[0].toUppercase())
    for (i in 1 ..< word.length)
      buffer.put(word[i].toLowercase())
  }
}
