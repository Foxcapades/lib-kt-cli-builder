package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.UByteArgument
import io.foxcapades.lib.cli.wrapper.flag.UByteFlag

internal class UByteFlagImpl : AbstractFlagImpl<UByteArgument, UByte>, UByteFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: UByteArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: UByteArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: UByteArgument)
    : super(shortForm, isRequired, argument)
}

