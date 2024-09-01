package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.LongArgument
import io.foxcapades.lib.cli.wrapper.flag.LongFlag

internal class LongFlagImpl : AbstractFlagImpl<LongArgument, Long>, LongFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: LongArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: LongArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: LongArgument)
    : super(shortForm, isRequired, argument)
}
