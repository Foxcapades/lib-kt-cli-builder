package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ULongArgument

internal class ULongFlagImpl : AbstractFlagImpl<ULongArgument, ULong>, ULongFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: ULongArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ULongArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: ULongArgument)
    : super(shortForm, isRequired, argument)
}

