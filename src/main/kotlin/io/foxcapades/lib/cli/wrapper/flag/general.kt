package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.*
import io.foxcapades.lib.cli.wrapper.arg.GeneralArgumentImpl
import io.foxcapades.lib.cli.wrapper.serial.*
import io.foxcapades.lib.cli.wrapper.util.CHAR_NULL
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.property

internal class GeneralFlagImpl<T>(
  override val hasLongForm: Boolean,
  override val longForm: String,
  override val hasShortForm: Boolean,
  override val shortForm: Char,
  override val argument: Argument<T>,
  override val isRequired: Boolean
) : Flag<Argument<T>, T> {
  constructor(
    longForm: Property<String>,
    shortForm: Property<Char>,
    isRequired: Property<Boolean>,
    argument: Argument<T>,
  ) : this(
    hasLongForm  = longForm.isSet,
    longForm     = longForm.getOr(""),
    hasShortForm = shortForm.isSet,
    shortForm    = shortForm.getOr(CHAR_NULL),
    isRequired   = isRequired.getOr(false),
    argument     = argument,
  )

  override fun writeToString(builder: CliAppender) {
    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, true)
    } else {
      builder.putLongFlag(longForm, true)
    }

    builder.putArgument(argument)
  }

  companion object {
    fun <T : Any> of(config: FlagOptions<T>): Flag<Argument<T>, T> {
      return GeneralFlagImpl(
        longForm = FlagOptions<T>::longForm.property(config),
        shortForm = FlagOptions<T>::shortForm.property(config),
        isRequired = FlagOptions<T>::requireFlag.property(config),
        argument = GeneralArgumentImpl(
          config.type,
          false,
          default = FlagOptions<T>::default.property(config),
          shouldQuote = FlagOptions<T>::shouldQuote.property(config),
          isRequired = FlagOptions<T>::requireArg.property(config),
          formatter = FlagOptions<T>::formatter.property(config),
        ),
      )
    }

    fun <T : Any> of(config: NullableFlagOptions<T>): Flag<Argument<T?>, T?> =
      GeneralFlagImpl(
        longForm     = FlagOptions<T>::longForm.property(config),
        shortForm    = FlagOptions<T>::shortForm.property(config),
        isRequired   = FlagOptions<T>::requireFlag.property(config),
        argument     = GeneralArgumentImpl(
          config.type,
          true,
          default     = FlagOptions<T>::default.property(config),
          shouldQuote = FlagOptions<T>::shouldQuote.property(config),
          isRequired  = FlagOptions<T>::requireArg.property(config),
          formatter   = FlagOptions<T>::formatter.property(config),
        ),
      )
  }
}

