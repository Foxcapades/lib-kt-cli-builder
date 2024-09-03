package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UShortArgument

internal class UShortFlagImpl : AbstractFlagImpl<UShortArgument, UShort>, UShortFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: UShortArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: UShortArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: UShortArgument)
    : super(shortForm, isRequired, argument)
}

