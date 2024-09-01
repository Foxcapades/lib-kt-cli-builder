package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.UIntArgument
import io.foxcapades.lib.cli.wrapper.flag.UIntFlag

internal class UIntFlagImpl : AbstractFlagImpl<UIntArgument, UInt>, UIntFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: UIntArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: UIntArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: UIntArgument)
    : super(shortForm, isRequired, argument)
}

