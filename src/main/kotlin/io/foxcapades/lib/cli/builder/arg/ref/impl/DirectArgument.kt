package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class DirectArgument<T : Any, V>(parent: ResolvedCommand<T>, delegate: Argument<V>, source: ValueSource)
  : ResolvedArgument<V>
  , AbstractArgument<V>(parent, delegate, source)
{
  override val parentComponent = parent
}
