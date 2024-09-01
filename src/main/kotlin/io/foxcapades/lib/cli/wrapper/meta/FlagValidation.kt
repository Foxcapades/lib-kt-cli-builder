package io.foxcapades.lib.cli.wrapper.meta

import io.foxcapades.lib.cli.wrapper.InvalidFlagFormException
import io.foxcapades.lib.cli.wrapper.serial.CliSerializationConfig
import io.foxcapades.lib.cli.wrapper.utils.isValidLongFlag
import io.foxcapades.lib.cli.wrapper.utils.isValidShortFlag
import io.foxcapades.lib.cli.wrapper.utils.safeName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

internal fun CliFlag.validateConfig(type: KClass<*>, property: KProperty1<*, *>, ctx: CliSerializationConfig) {
  if (hasShortForm) {
    if (hasLongForm) {
      flagValidationBoth(longForm, shortForm, property, type, ctx)
    } else {
      flagValidationShort(shortForm, property, type)
    }
  } else if (hasLongForm) {
    flagValidationLong(longForm, property, type, ctx)
  }
}

internal fun flagValidationShort(
  shortForm: Char,
  property: KProperty1<*, *>,
  type: KClass<*>,
) {
  if (!shortForm.isValidShortFlag) {
    throw InvalidFlagFormException.invalidShortForm(property.name, type.safeName, shortForm)
  }
}

internal fun flagValidationLong(
  longForm: String,
  property: KProperty1<*, *>,
  type: KClass<*>,
  ctx: CliSerializationConfig,
) {
  if (longForm.contains(ctx.longFlagValueSeparator) || !longForm.isValidLongFlag) {
    TODO("throw exception for invalid long form")
  }
}

internal fun flagValidationBoth(
  longForm: String,
  shortForm: Char,
  property: KProperty1<*, *>,
  type: KClass<*>,
  ctx: CliSerializationConfig,
) {
  val shortIsValid = shortForm.isValidShortFlag
  val longIsValid = !longForm.contains(ctx.longFlagValueSeparator)
    && longForm.isValidLongFlag

  // If the short flag is not valid
  if (!shortIsValid) {
    // AND the long flag is also not valid
    if (!longIsValid) {
      // throw an exception for both the short and long flag values
      TODO("throw exception for both short flag and long flag being invalid")
    }

    // ELSE the long flag IS valid
    else {
      // throw an exception for just the short flag
      throw InvalidFlagFormException.invalidShortForm(property.name, type.safeName, shortForm)
    }
  } else if (!longIsValid) {
    TODO("throw exception for long flag being invalid")
  }
}