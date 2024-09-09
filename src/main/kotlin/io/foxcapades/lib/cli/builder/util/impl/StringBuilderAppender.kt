package io.foxcapades.lib.cli.builder.util.impl

import io.foxcapades.lib.cli.builder.util.CharacterAppender

@JvmInline
internal value class StringBuilderAppender(private val builder: StringBuilder) : CharacterAppender {
  override fun append(char: Char) {
    builder.append(char)
  }

  override fun append(string: CharSequence) {
    builder.append(string)
  }

  override fun append(string: CharArray) {
    builder.append(string)
  }
}
