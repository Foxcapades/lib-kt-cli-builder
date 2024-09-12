package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal open class UnlinkedArgument<V>(parent: ResolvedComponent, instance: Argument<V>, source: ValueSource)
  : UnlinkedResolvedArgument<V>
  , AbstractArgument<V>(parent, instance, source)
