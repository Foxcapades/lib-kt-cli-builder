package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.util.Property

internal class ComplexFlagImpl<T> : AbstractFlagImpl<ComplexArgument<T>, T>, ComplexFlag<ComplexArgument<T>, T> {
  constructor(lf: Property<String>, sf: Property<Char>, isRequired: Property<Boolean>, argument: ComplexArgument<T>)
    : super(lf, sf, isRequired, argument)

  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: ComplexArgument<T>)
    : super(shortForm, isRequired, argument)
}

