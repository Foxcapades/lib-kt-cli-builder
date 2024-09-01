package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.flag.ComplexFlag
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument

internal class ComplexFlagImpl<T> : AbstractFlagImpl<ComplexArgument<T>, T>, ComplexFlag<ComplexArgument<T>, T> {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(shortForm, isRequired, argument)
}

