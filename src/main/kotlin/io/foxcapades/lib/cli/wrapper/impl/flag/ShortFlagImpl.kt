package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.ShortArgument
import io.foxcapades.lib.cli.wrapper.flag.ShortFlag

internal class ShortFlagImpl : AbstractFlagImpl<ShortArgument, Short>, ShortFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: ShortArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ShortArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: ShortArgument)
    : super(shortForm, isRequired, argument)
}

