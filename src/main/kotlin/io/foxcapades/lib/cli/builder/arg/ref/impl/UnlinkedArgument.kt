package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.util.values.ValueSource

// An argument instance for which the actual class container is unknown
internal open class UnlinkedArgument<V>(parent: ResolvedComponent, instance: Argument<V>, source: ValueSource)
  : ResolvedArgument<V>
  , AbstractArgument<V>(parent, instance, source)
