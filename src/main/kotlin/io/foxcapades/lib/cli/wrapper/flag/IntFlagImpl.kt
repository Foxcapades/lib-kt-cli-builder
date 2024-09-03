package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.IntArgument

internal class IntFlagImpl : AbstractFlagImpl<IntArgument, Int>, IntFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: IntArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: IntArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: IntArgument)
    : super(shortForm, isRequired, argument)
}

