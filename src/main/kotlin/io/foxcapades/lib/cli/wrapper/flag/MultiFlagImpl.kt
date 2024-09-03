package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument

internal class MultiFlagImpl<V> : AbstractFlagImpl<MultiArgument<V>, Iterable<V>>, MultiFlag<V> {
  constructor(longForm: String, shortForm: Char, isRequired: Boolean, argument: MultiArgument<V>)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: MultiArgument<V>)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Char, isRequired: Boolean, argument: MultiArgument<V>)
    : super(shortForm, isRequired, argument)
}
