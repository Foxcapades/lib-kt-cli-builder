package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.flag.FloatFlag
import io.foxcapades.lib.cli.wrapper.arg.FloatArgument

internal class FloatFlagImpl : AbstractFlagImpl<FloatArgument, Float>, FloatFlag {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: FloatArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: FloatArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: FloatArgument)
    : super(shortForm, isRequired, argument)
}
