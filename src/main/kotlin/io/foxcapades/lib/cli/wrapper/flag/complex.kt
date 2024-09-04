package io.foxcapades.lib.cli.wrapper.flag

import io.foxcapades.lib.cli.wrapper.Flag
import io.foxcapades.lib.cli.wrapper.arg.ComplexArgument
import io.foxcapades.lib.cli.wrapper.util.Property

interface ComplexFlag<A: ComplexArgument<V>, V> : Flag<A, V>

internal class ComplexFlagImpl<T>(
  longForm: Property<String>,
  shortForm: Property<Char>,
  isRequired: Property<Boolean>,
  argument: ComplexArgument<T>
)
  : AbstractFlagImpl<ComplexArgument<T>, T>(longForm, shortForm, isRequired, argument)
  , ComplexFlag<ComplexArgument<T>, T>
