package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.UByteArgument

internal class UByteFlagImpl : AbstractFlagImpl<UByteArgument, UByte>, UByteFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: UByteArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: UByteArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: UByteArgument)
    : super(shortForm, isRequired, argument)
}

