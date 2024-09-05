package io.foxcapades.lib.cli.wrapper.serial

/**
 * CLI Argument Value Appender.
 *
 * Like a [StringBuilder], but for writing out argument values.
 *
 * @since 1.0.0
 */
interface CliArgumentAppender {
  /**
   * Current serialization config.
   */
  val config: CliSerializationConfig

  /**
   * Appends the given character to the rendered argument value.
   *
   * @param char Character to add.
   *
   * @return This `CliArgumentAppender` instance.
   */
  fun appendChar(char: Char): CliArgumentAppender

  /**
   * Appends the given byte character to the rendered argument value.
   *
   * This value is treated as a character, not an int.
   *
   * @param char Character to add.
   *
   * @return This `CliArgumentAppender` instance.
   */
  fun appendChar(char: Byte): CliArgumentAppender

  /**
   * Appends the given string to the rendered argument value.
   *
   * @param string String to add.
   *
   * @return This `CliArgumentAppender` instance.
   */
  fun appendString(string: CharSequence): CliArgumentAppender
}
