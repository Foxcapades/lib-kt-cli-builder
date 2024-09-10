package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

/**
 * Argument formatters are used to control how argument values are serialized
 * into CLI call strings.
 *
 * @param V Argument value type.
 *
 * @since 1.0.0
 */
fun interface ArgumentFormatter<V> {

  /**
   * Called to format a value and write it to the given writer instance.
   *
   * @param value Value to write.
   *
   * @param writer CLI writer into which [value] should be written.
   *
   * @param reference The argument instance being formatted.
   */
  fun formatValue(value: V, writer: CliArgumentWriter<*, V>, reference: Argument<V>)
}
