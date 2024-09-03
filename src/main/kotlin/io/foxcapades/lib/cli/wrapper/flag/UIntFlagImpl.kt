package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UIntArgument

internal class UIntFlagImpl : AbstractFlagImpl<UIntArgument, UInt>, UIntFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: UIntArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: UIntArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: UIntArgument)
    : super(shortForm, isRequired, argument)
}

