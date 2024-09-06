package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.arg.MultiArgument
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property

interface MultiFlag<A : MultiArgument<V>, V> : ComplexFlag<A, Iterable<V>>

internal class MultiFlagImpl<A : MultiArgument<V>, V>(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<MultiFlag<A, V>, A, Iterable<V>>>,
  argument:   A,
)
  : AbstractFlagImpl<MultiFlag<A, V>, A, Iterable<V>>(longForm, shortForm, isRequired, filter, argument)
  , MultiFlag<A, V>
