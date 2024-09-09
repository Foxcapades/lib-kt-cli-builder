package io.foxcapades.lib.cli.builder.arg.format

import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter

internal class UnconfiguredArgFormatter : ArgumentFormatter<Any?> {
  override fun formatValue(value: Any?, writer: CliArgumentWriter<*, Any?>) =
    NullableGeneralStringifier.formatValue(value, writer)
}
