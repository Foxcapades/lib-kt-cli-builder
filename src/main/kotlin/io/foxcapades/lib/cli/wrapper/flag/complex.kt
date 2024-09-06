package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.serial.values.FlagPredicate
import io.foxcapades.lib.cli.wrapper.util.Property

interface ComplexFlag<A: ComplexArgument<V>, V> : Flag<A, V>

internal class ComplexFlagImpl<A: ComplexArgument<V>, V>(
  longForm:   Property<String>,
  shortForm:  Property<Char>,
  isRequired: Property<Boolean>,
  filter:     Property<FlagPredicate<ComplexFlag<A, V>, A, V>>,
  argument:   A
)
  : AbstractFlagImpl<ComplexFlag<A, V>, A, V>(longForm, shortForm, isRequired, filter, argument)
  , ComplexFlag<A, V>
