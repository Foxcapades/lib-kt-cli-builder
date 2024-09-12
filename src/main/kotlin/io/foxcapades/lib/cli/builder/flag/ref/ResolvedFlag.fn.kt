package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.InvalidFlagFormException
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig

// region Validation

internal fun ResolvedFlag<*>.validateFlagNames(config: CliSerializationConfig) {
  if (hasShortForm) {
    val sv = config.targetShell.isFlagSafe(shortForm)

    if (hasLongForm) {
      var lv = true

      for (c in longForm) {
        if (!config.targetShell.isFlagSafe(c)) {
          lv = false
          break
        }
      }

      if (!sv) {
        throw if (!lv)
          InvalidFlagFormException.invalidBothForms(this)
        else
          InvalidFlagFormException.invalidShortForm(this)
      }
    }
  } else if (hasLongForm) {
    var lv = true

    for (c in longForm) {
      if (!config.targetShell.isFlagSafe(c)) {
        lv = false
        break
      }
    }

    if (!lv)
      throw InvalidFlagFormException.invalidLongForm(this)
  } else if (!valueSource.hasName) {
    throw IllegalStateException("Flag instance has no short or long form defined; sourced from ${parentComponent.qualifiedName}")
  }
}

// endregion Validation

// region Unsafe Casting

@Suppress("UNCHECKED_CAST")
internal fun <T : ResolvedFlag<V>, V> T.forceAny() = this as ResolvedFlag<Any?>

// endregion Unsafe Casting


