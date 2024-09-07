package io.foxcapades.lib.cli.builder.arg.properties

import io.foxcapades.lib.cli.builder.util.toLowercase
import io.foxcapades.lib.cli.builder.util.toUppercase
import java.nio.CharBuffer

internal class PascalCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    buffer.put(word[0].toUppercase())
    for (i in 1 ..< word.length)
      buffer.put(word[i].toLowercase())
  }
}
