package io.foxcapades.lib.cli.wrapper.serial

/**
 * A writer that may be used to write out a flag to a CLI call being serialized.
 *
 * @param T Type of the instance that contains the CLI flag.
 *
 * @param V Type of value contained by the flag.
 *
 * @since 1.0.0
 */
interface CliArgumentWriter<T : Any, V> : CliWriterContext<T, V>, CliWriter {
  /**
   * Writes out a single 1-byte character as part of the current argument value.
   *
   * @param char Character to write.
   */
  fun writeChar(char: Byte)

  /**
   * Writes out a single character as part of the current argument value.
   *
   * @param char Character to write.
   */
  fun writeChar(char: Char)

  /**
   * Writes out a string as part of the current argument value.
   *
   * @param string String to write.
   */
  fun writeString(string: CharSequence)
}
