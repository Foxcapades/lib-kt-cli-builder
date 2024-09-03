package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.FloatArgument

internal class FloatFlagImpl : AbstractFlagImpl<FloatArgument, Float>, FloatFlag {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: FloatArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: FloatArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: FloatArgument)
    : super(shortForm, isRequired, argument)
}
