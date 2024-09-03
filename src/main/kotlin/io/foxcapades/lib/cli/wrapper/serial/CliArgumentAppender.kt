package io.foxcapades.lib.cli.wrapper.serial

interface CliArgumentAppender {
  val config: CliSerializationConfig

  fun appendChar(char: Char): CliArgumentAppender

  fun appendChar(char: Byte): CliArgumentAppender

  fun appendString(string: CharSequence): CliArgumentAppender
}