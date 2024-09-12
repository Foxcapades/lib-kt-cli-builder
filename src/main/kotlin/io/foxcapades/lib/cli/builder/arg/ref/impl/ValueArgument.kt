package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.util.values.ValueSource

// an argument that is the value of a member, i.e. val foo: Argument<V> or fun getFoo(): Argument<V>
internal class ValueArgument<V>(parent: ResolvedComponent, instance: Argument<V>, source: ValueSource)
  : ResolvedArgument<V>
  , AbstractArgument<V>(parent, instance, source)
