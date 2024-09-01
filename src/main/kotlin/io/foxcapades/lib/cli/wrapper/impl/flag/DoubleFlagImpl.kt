package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.flag.DoubleFlag

internal class DoubleFlagImpl : AbstractFlagImpl<DoubleArgument, Double>, DoubleFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: DoubleArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: DoubleArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: DoubleArgument)
    : super(shortForm, isRequired, argument)
}
