package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.CharArgument
import io.foxcapades.lib.cli.wrapper.util.Property

internal class CharFlagImpl : AbstractFlagImpl<CharArgument, Char>, CharFlag {
  constructor(lf: Property<String>, sf: Property<Char>, isRequired: Property<Boolean>, argument: CharArgument)
    : super(lf, sf, isRequired, argument)

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: CharArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: CharArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: CharArgument)
    : super(shortForm, isRequired, argument)
}

