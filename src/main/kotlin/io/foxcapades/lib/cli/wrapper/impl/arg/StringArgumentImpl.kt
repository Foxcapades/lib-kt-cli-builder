package io.foxcapades.lib.cli.wrapper.impl.arg

import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder
import io.foxcapades.lib.cli.wrapper.serial.values.ValueFormatter

internal class StringArgumentImpl : AbstractScalarArgument<String>, StringArgument {
  constructor(default: String, formatter: ValueFormatter<String>)
    : super("", default, true, formatter)

  constructor(default: String)
    : super("", default, true, ValueFormatter(String::toString))

  constructor(formatter: ValueFormatter<String>)
    : super("", "", false, formatter)

  constructor()
    : super("", "", false, ValueFormatter(String::toString))

  override fun writeToString(builder: CliStringBuilder) {
    builder.append('\'')
    var dub = false

    for (c in formatter(safeGet(), builder.config)) {
      if (c == '\'') {
        // If we aren't already in double quotes
        if (!dub) {
          // switch to double quotes
          builder.append("'\"")
          dub = true
        }

        // append the apos
        builder.append(c)
      } else {
        // if we are in double quotes
        if (dub) {
          // exit double quotes
          builder.append("\"'")
          dub = false
        }

        builder.append(c)
      }
    }

    if (dub)
      builder.append('"')
    else
      builder.append('\'')
  }
}