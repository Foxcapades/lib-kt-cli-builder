package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.flag.CharFlag
import io.foxcapades.lib.cli.wrapper.arg.CharArgument

internal class CharFlagImpl : AbstractFlagImpl<CharArgument, Char>, CharFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: CharArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: CharArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: CharArgument)
    : super(shortForm, isRequired, argument)
}

