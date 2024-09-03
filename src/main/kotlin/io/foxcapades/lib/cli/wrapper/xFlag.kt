package io.foxcapades.lib.cli.wrapper

import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig

internal fun Flag<*, *>.safeName(config: CliSerializationConfig) =
  if (hasLongForm)
    config.longFlagPrefix + longForm
  else
    config.shortFlagPrefix + shortForm