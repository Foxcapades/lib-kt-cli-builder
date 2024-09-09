package io.foxcapades.lib.cli.builder.util

/**
 * Defines a function that may be called to append a character to a buffer or
 * stream.
 *
 * Simple example:
 * ```kt
 * val bld = StringBuilder()
 *
 * funThatTakesCharAppender(bld::append)
 * ```
 *
 * @since 1.0.0
 */
fun interface CharacterAppender {

  /**
   * Appends the given character to the underlying buffer or stream.
   *
   * @param char Character to append.
   */
  fun append(char: Char)

  /**
   * Convenience method for appending multiple characters at once.
   *
   * By default, this method simply calls `append(Char)` in a loop for each
   * character in the given string, however explicit implementations of this
   * interface may implement this in a more effective manner.
   *
   * @param string String of characters to append.
   */
  fun append(string: CharSequence) = string.forEach(::append)

  /**
   * Convenience method for appending multiple characters at once.
   *
   * By default, this method simply calls `append(Char)` in a loop for each
   * character in the given array, however explicit implementations of this
   * interface may implement this in a more effective manner.
   *
   * @param string Array of characters to append.
   */
  fun append(string: CharArray) = string.forEach(::append)
}
