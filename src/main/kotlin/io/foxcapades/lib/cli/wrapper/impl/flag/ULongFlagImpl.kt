package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.ULongArgument
import io.foxcapades.lib.cli.wrapper.flag.ULongFlag

internal class ULongFlagImpl : AbstractFlagImpl<ULongArgument, ULong>, ULongFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: ULongArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ULongArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: ULongArgument)
    : super(shortForm, isRequired, argument)
}

