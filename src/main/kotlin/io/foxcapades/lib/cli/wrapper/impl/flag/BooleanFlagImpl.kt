package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.BooleanArgument
import io.foxcapades.lib.cli.wrapper.flag.BooleanFlag
import io.foxcapades.lib.cli.wrapper.serial.CliStringBuilder

internal class BooleanFlagImpl : AbstractFlagImpl<BooleanArgument, Boolean>, BooleanFlag {
  override val isToggleFlag: Boolean

  constructor(
    longForm: String,
    shortForm: Byte,
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
    shortForm: Byte,
    isRequired: Boolean,
    isToggle: Boolean,
    argument: BooleanArgument,
  ) : super(shortForm, isRequired, argument) {
    isToggleFlag = isToggle
  }

  override fun writeToString(builder: CliStringBuilder) {
    if (!isToggleFlag)
      return super.writeToString(builder)

    if (argument.isDefault)
      return

    if (!hasLongForm || builder.config.preferredFlagForm.isShort && hasShortForm) {
      builder.appendShortFlag(shortForm, false)
    } else {
      builder.appendLongFlag(longForm, false)
    }
  }
}

