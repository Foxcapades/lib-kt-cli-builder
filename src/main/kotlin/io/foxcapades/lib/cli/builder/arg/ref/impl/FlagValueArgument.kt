package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.flag.ref.ResolvedFlag
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class FlagValueArgument<V>(parent: ResolvedFlag<V>, instance: Argument<V>, source: ValueSource)
  : ResolvedArgument<V>
  , AbstractArgument<V>(parent, instance, source)
{
  @Suppress("UNCHECKED_CAST")
  override val parentComponent: ResolvedFlag<V>
    get() = super.parentComponent as ResolvedFlag<V>

  override val qualifiedName: String
    get() = "argument of " + parentComponent.qualifiedName
}
