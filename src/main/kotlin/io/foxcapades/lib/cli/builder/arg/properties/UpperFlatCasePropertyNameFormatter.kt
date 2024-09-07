package io.foxcapades.lib.cli.builder.arg.properties

import io.foxcapades.lib.cli.builder.util.toUppercase
import java.nio.CharBuffer

internal class UpperFlatCasePropertyNameFormatter : AbstractPropertyNameFormatter() {
  override fun bufferSize(name: String) = name.length * 2

  override fun subsequentWord(word: String, buffer: CharBuffer) {
    for (c in word)
      buffer.put(c.toUppercase())
  }
}
