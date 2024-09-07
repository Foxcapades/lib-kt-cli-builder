package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

/**
 * @param V Argument value type.
 */
fun interface ArgumentFormatter<V> {
  fun formatValue(value: V, builder: CliArgumentWriter<*, V>)
}
