package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.StringArgument
import io.foxcapades.lib.cli.wrapper.flag.StringFlag

internal class StringFlagImpl : AbstractFlagImpl<StringArgument, String>, StringFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: StringArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: StringArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: StringArgument)
    : super(shortForm, isRequired, argument)
}

