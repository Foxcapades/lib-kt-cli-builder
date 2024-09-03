package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.LongArgument

internal class LongFlagImpl : AbstractFlagImpl<LongArgument, Long>, LongFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: LongArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: LongArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: LongArgument)
    : super(shortForm, isRequired, argument)
}
