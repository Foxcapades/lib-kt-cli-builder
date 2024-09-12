package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.FlagValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class ValueFlag<T : Any, V>(
  parent:   ResolvedCommand<T>,
  instance: Flag<V>,
  source: ValueSource,
)
  : ResolvedFlag<V>
  , AbstractValueFlag<T, V>(
    parent,
    instance,
    source,
  )
{
  override val argument = FlagValueArgument(this, instance.argument, source)
}
