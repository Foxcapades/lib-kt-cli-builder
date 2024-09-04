package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.util.Property

interface MultiFlag<V> : ComplexFlag<MultiArgument<V>, Iterable<V>>

internal class MultiFlagImpl<V>(
  longForm:   Property<String> = Property.empty(),
  shortForm:  Property<Char> = Property.empty(),
  isRequired: Property<Boolean> = Property.empty(),
  argument:   MultiArgument<V>,
)
  : AbstractFlagImpl<MultiArgument<V>, Iterable<V>>(longForm, shortForm, isRequired, argument)
  , MultiFlag<V>
