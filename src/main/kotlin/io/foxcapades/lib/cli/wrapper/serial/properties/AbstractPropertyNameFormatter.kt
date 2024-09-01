package io.foxcapades.lib.cli.wrapper.serial.properties

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import java.nio.CharBuffer

internal abstract class AbstractPropertyNameFormatter : PropertyNameFormatter {
  private val rgx by lazy { Regex("([^a-z_][^A-Z_]+|[^a-z_]+(?![a-z])|[^A-Z_]+)") }

  protected abstract fun bufferSize(name: String): Int

  protected open fun firstWord(word: String, buffer: CharBuffer) = subsequentWord(word, buffer)

  protected open fun wordSeparator(buffer: CharBuffer) {}

  protected abstract fun subsequentWord(word: String, buffer: CharBuffer)

  @Suppress("FoldInitializerAndIfToElvis")
  override fun invoke(name: String, serializationConfig: CliSerializationConfig): String {
    if (name.isEmpty())
      return name

    val buffer = CharBuffer.allocate(bufferSize(name))
    var match = rgx.find(name)

    if (match == null)
      return name

    firstWord(match.value, buffer)

    match = match.next()
    while (match != null) {
      wordSeparator(buffer)
      subsequentWord(match.value, buffer)
      match = match.next()
    }

    buffer.flip()
    return buffer.toString()
  }
}