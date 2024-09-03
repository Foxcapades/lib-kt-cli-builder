package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.DoubleArgument
import io.foxcapades.lib.cli.wrapper.util.Property

internal class DoubleFlagImpl : AbstractFlagImpl<DoubleArgument, Double>, DoubleFlag {
  constructor(lf: Property<String>, sf: Property<Char>, isRequired: Property<Boolean>, argument: DoubleArgument)
    : super(lf, sf, isRequired, argument)

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: DoubleArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: DoubleArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: DoubleArgument)
    : super(shortForm, isRequired, argument)
}
