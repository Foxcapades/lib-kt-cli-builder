package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.serial.CliAppender
import io.foxcapades.lib.cli.wrapper.util.Property

internal class BooleanFlagImpl : AbstractFlagImpl<BooleanArgument, Boolean>, BooleanFlag {
  override val isToggleFlag: Boolean

  constructor(
    longForm: Property<String>,
    shortForm: Property<Char>,
    isRequired: Property<Boolean>,
    isToggle: Boolean,
    argument: BooleanArgument,
  ) : super(longForm, shortForm, isRequired, argument) {
    isToggleFlag = isToggle
  }

  constructor(
    longForm: String,
    shortForm: Char,
    isRequired: Boolean,
    isToggle: Boolean,
    argument: BooleanArgument
  ) : super(longForm, shortForm, isRequired, argument) {
    isToggleFlag = isToggle
  }

  constructor(
    longForm: String,
    isRequired: Boolean,
    isToggle: Boolean,
    argument: BooleanArgument,
  ) : super(longForm, isRequired, argument) {
    isToggleFlag = isToggle
  }

  constructor(
    shortForm: Char,
    isRequired: Boolean,
    isToggle: Boolean,
    argument: BooleanArgument,
  ) : super(shortForm, isRequired, argument) {
    isToggleFlag = isToggle
  }

  override fun writeToString(builder: CliAppender) {
    if (!isToggleFlag)
      return super.writeToString(builder)

    if (argument.isDefault(builder.config))
      return

    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.putShortFlag(shortForm, false)
    } else {
      builder.putLongFlag(longForm, false)
    }
  }
}

