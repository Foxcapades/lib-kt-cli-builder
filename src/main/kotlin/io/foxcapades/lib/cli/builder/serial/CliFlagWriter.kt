package io.foxcapades.lib.cli.builder.serial

/**
 * A writer that may be used to write out a flag to a CLI call being serialized.
 *
 * @param T Type of the instance that contains the CLI flag.
 *
 * @param V Type of value contained by the flag.
 *
 * @since 1.0.0
 */
interface CliFlagWriter<T : Any, V> : CliWriterContext<T, V>, CliWriter {
  /**
   * Writes the preferred flag form to the writer.
   *
   * If the flag does not have the preferred form, but does have the opposite
   * form, that opposite form will be used.
   *
   * If the flag does not have the preferred form, and also does not have the
   * opposite form, the flag name will be derived from the source property or
   * method.
   *
   * @return A [CliArgumentWriter] instance which may be used to append a value
   * to the flag being written.
   */
  fun writePreferredForm(): CliArgumentWriter<T, V>

  /**
   * Writes the preferred flag form to the writer.
   *
   * If the flag does not have the preferred form, but does have the opposite
   * form, that opposite form will be used.
   *
   * If the flag does not have the preferred form, and also does not have the
   * opposite form, the flag name will be derived from the source property or
   * method.
   *
   * @param action A function that will be called with a [CliArgumentWriter]
   * instance which may be used to append a value to the flag being written.
   */
  fun writePreferredForm(action: CliArgumentWriter<T, V>.() -> Unit)

  /**
   * Writes the flag's long form to the writer, regardless of the configured
   * flag form preference ([CliSerializationConfig.preferredFlagForm]).
   *
   * If the flag does not have a long form, an exception will be thrown.
   *
   * @return A [CliArgumentWriter] instance which may be used to append a value
   * to the flag being written.
   */
  fun writeLongForm(): CliArgumentWriter<T, V>

  /**
   * Writes the flag's long form to the writer, regardless of the configured
   * flag form preference ([CliSerializationConfig.preferredFlagForm]).
   *
   * If the flag does not have a long form, an exception will be thrown.
   *
   * @param action A function that will be called with a [CliArgumentWriter]
   * instance which may be used to append a value to the flag being written.
   */
  fun writeLongForm(action: CliArgumentWriter<T, V>.() -> Unit)

  /**
   * Writes a custom long form flag name to the writer to represent the current
   * flag.
   *
   * @param custom Custom long form to represent the current flag.
   *
   * @return A [CliArgumentWriter] instance which may be used to append a value
   * to the flag being written.
   */
  fun writeLongForm(custom: String): CliArgumentWriter<T, V>

  /**
   * Writes a custom long form flag name to the writer to represent the current
   * flag.
   *
   * @param custom Custom long form to represent the current flag.
   *
   * @param action A function that will be called with a [CliArgumentWriter]
   * instance which may be used to append a value to the flag being written.
   */
  fun writeLongForm(custom: String, action: CliArgumentWriter<T, V>.() -> Unit)

  /**
   * Writes the flag's short form to the writer, regardless of the configured
   * flag form preference ([CliSerializationConfig.preferredFlagForm]).
   *
   * If the flag does not have a short form, an exception will be thrown.
   *
   * @return A [CliArgumentWriter] instance which may be used to append a value
   * to the flag being written.
   */
  fun writeShortForm(): CliArgumentWriter<T, V>

  /**
   * Writes the flag's short form to the writer, regardless of the configured
   * flag form preference ([CliSerializationConfig.preferredFlagForm]).
   *
   * If the flag does not have a short form, an exception will be thrown.
   *
   * @param action A function that will be called with a [CliArgumentWriter]
   * instance which may be used to append a value to the flag being written.
   */
  fun writeShortForm(action: CliArgumentWriter<T, V>.() -> Unit)

  /**
   * Writes a custom short form flag name to the writer to represent the current
   * flag.
   *
   * @param custom Custom short form to represent the current flag.
   *
   * @return A [CliArgumentWriter] instance which may be used to append a value
   * to the flag being written.
   */
  fun writeShortForm(custom: Char): CliArgumentWriter<T, V>

  /**
   * Writes a custom short form flag name to the writer to represent the current
   * flag.
   *
   * @param custom Custom short form to represent the current flag.
   *
   * @param action A function that will be called with a [CliArgumentWriter]
   * instance which may be used to append a value to the flag being written.
   */
  fun writeShortForm(custom: Char, action: CliArgumentWriter<T, V>.() -> Unit)
}
