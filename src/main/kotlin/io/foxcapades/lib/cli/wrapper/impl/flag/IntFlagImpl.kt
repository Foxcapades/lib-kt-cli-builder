package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.flag.IntFlag
import io.foxcapades.lib.cli.wrapper.arg.IntArgument

internal class IntFlagImpl : AbstractFlagImpl<IntArgument, Int>, IntFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: IntArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: IntArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: IntArgument)
    : super(shortForm, isRequired, argument)
}

