package io.foxcapades.lib.cli.wrapper.serial

interface CliWriter {
  /**
   * Writes a raw 1-byte character value to the CLI call being serialized.
   *
   * This value will be considered a complete, standalone CLI call component and
   * will be separated from any previous or subsequent CLI components in the
   * serialized call.
   *
   * **Examples**
   *
   * *String Form*
   * ```kt
   * writer.rawWriteChar('a') // call -> "a"
   * writer.rawWriteChar('b') // call -> "a b"
   * writer.rawWriteChar('c') // call -> "a b c"
   * ```
   *
   * *List Form*
   * ```kt
   * writer.rawWriteChar('a') // call -> ["a"]
   * writer.rawWriteChar('b') // call -> ["a", "b"]
   * writer.rawWriteChar('c') // call -> ["a", "b", "c"]
   * ```
   *
   * @param char Character to write.
   */
  fun rawWriteChar(char: Byte)

  /**
   * Writes a raw character value to the CLI call being serialized.
   *
   * This value will be considered a complete, standalone CLI call component and
   * will be separated from any previous or subsequent CLI components in the
   * serialized call.
   *
   * **Examples**
   *
   * *String Form*
   * ```kt
   * writer.rawWriteChar('a') // call -> "a"
   * writer.rawWriteChar('b') // call -> "a b"
   * writer.rawWriteChar('c') // call -> "a b c"
   * ```
   *
   * *List Form*
   * ```kt
   * writer.rawWriteChar('a') // call -> ["a"]
   * writer.rawWriteChar('b') // call -> ["a", "b"]
   * writer.rawWriteChar('c') // call -> ["a", "b", "c"]
   * ```
   *
   * @param char Character to write.
   */
  fun rawWriteChar(char: Char)

  /**
   * Writes a raw string value to the CLI call being serialized.
   *
   * This value will be considered a complete, standalone CLI call component and
   * will be separated from any previous or subsequent CLI components in the
   * serialized call.
   *
   * **Examples**
   *
   * *String Form*
   * ```kt
   * writer.rawWriteString("goodbye") // call -> "goodbye"
   * writer.rawWriteString("cruel")   // call -> "goodbye cruel"
   * writer.rawWriteString("world")   // call -> "goodbye cruel world"
   * ```
   *
   * *List Form*
   * ```kt
   * writer.rawWriteString("goodbye") // call -> ["goodbye"]
   * writer.rawWriteString("cruel")   // call -> ["goodbye", "cruel"]
   * writer.rawWriteString("world")   // call -> ["goodbye", "cruel", "world"]
   * ```
   *
   * @param string String to write.
   */
  fun rawWriteString(string: CharSequence)
}
