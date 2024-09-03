package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ByteArgument
import io.foxcapades.lib.cli.wrapper.util.Property

internal class ByteFlagImpl : AbstractFlagImpl<ByteArgument, Byte>, ByteFlag {
  constructor(lf: Property<String>, sf: Property<Char>, isRequired: Property<Boolean>, argument: ByteArgument)
    : super(lf, sf, isRequired, argument)

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: ByteArgument)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ByteArgument)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: ByteArgument)
    : super(shortForm, isRequired, argument)
}
