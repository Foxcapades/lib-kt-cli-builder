package io.foxcapades.lib.cli.wrapper.impl.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.flag.MultiFlag

internal class MultiFlagImpl<V> : AbstractFlagImpl<MultiArgument<V>, Iterable<V>>, MultiFlag<V> {
  constructor(longForm: String, shortForm: Byte, isRequired: Boolean, argument: MultiArgument<V>)
    : super(longForm, shortForm, isRequired, argument)

  constructor(longForm: String, isRequired: Boolean, argument: MultiArgument<V>)
    : super(longForm, isRequired, argument)

  constructor(shortForm: Byte, isRequired: Boolean, argument: MultiArgument<V>)
    : super(shortForm, isRequired, argument)
}
