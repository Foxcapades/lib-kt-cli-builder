package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.InvalidFlagFormException
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig

// region Validation

internal fun ResolvedFlag<*>.validateFlagNames(config: CliSerializationConfig) {
  val sv = !hasShortForm || config.targetShell.isFlagSafe(shortForm)
  var lv = true

  if (hasLongForm) {
    for (c in longForm) {
      if (!config.targetShell.isFlagSafe(c)) {
        lv = false
        break
      }
    }
  }

  if (!sv) {
    throw if (!lv)
      InvalidFlagFormException.invalidBothForms(this)
    else
      InvalidFlagFormException.invalidShortForm(this)
  } else if (!lv) {
    throw InvalidFlagFormException.invalidLongForm(this)
  }
}

// endregion Validation

// region Unsafe Casting

@Suppress("UNCHECKED_CAST")
internal fun <T : ResolvedFlag<V>, V> T.forceAny() = this as ResolvedFlag<Any?>

// endregion Unsafe Casting


