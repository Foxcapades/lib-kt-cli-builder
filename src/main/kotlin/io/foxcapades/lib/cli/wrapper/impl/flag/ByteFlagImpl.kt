package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.flag.ByteFlag
import io.foxcapades.lib.cli.wrapper.arg.ByteArgument

internal class ByteFlagImpl : AbstractFlagImpl<ByteArgument, Byte>, ByteFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: ByteArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ByteArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: ByteArgument)
    : super(shortForm, isRequired, argument)
}

