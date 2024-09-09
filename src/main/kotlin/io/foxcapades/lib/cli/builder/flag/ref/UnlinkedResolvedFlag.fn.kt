package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig

// region Validation

internal fun UnlinkedResolvedFlag<*>.validateFlagNames(config: CliSerializationConfig) {
  if (hasShortForm || hasLongForm)
    (this as ResolvedFlag<*>).validateFlagNames(config)
  else
    // TODO: this should be a typed exception!
    throw IllegalStateException("unlinked flag instance has no short or long form defined; sourced from ${parentComponent.qualifiedName}")
}

// endregion Validation
