package io.foxcapades.lib.cli.builder.flag.impl

import io.foxcapades.lib.cli.builder.arg.MultiArgument
import io.foxcapades.lib.cli.builder.flag.MultiFlag
import io.foxcapades.lib.cli.builder.flag.filter.FlagPredicate
import io.foxcapades.lib.cli.builder.util.properties.Property

internal class MultiFlagImpl<A : MultiArgument<I, V>, I : Iterable<V>, V>(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<MultiFlag<A, I, V>, A, I>>,
  argument:   A,
)
  : AbstractFlagImpl<MultiFlag<A, I, V>, A, I>(longForm, shortForm, isRequired, filter, argument)
  , MultiFlag<A, I, V>
