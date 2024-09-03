package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.StringArgument

internal class StringFlagImpl : AbstractFlagImpl<StringArgument, String>, StringFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: StringArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: StringArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: StringArgument)
    : super(shortForm, isRequired, argument)
}

