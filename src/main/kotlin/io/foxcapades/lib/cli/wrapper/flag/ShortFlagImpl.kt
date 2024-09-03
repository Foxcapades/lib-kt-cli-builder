package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ShortArgument

internal class ShortFlagImpl : AbstractFlagImpl<ShortArgument, Short>, ShortFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: ShortArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ShortArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: ShortArgument)
    : super(shortForm, isRequired, argument)
}

