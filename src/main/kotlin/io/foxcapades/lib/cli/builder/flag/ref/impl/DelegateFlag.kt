package io.foxcapades.lib.cli.builder.flag.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.FlagValueArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.util.values.ValueAccessor

internal class DelegateFlag<T : Any, V>(
  parent:   ResolvedCommand<T>,
  delegate: Flag<V>,
  source: ValueAccessor<V>,
)
  : ResolvedFlag<V>
  , AbstractValueFlag<T, V>(parent, delegate, source)
{
  override val valueSource = source
  override val argument = FlagValueArgument(this, delegate.argument, source)
}
