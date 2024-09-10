package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand

/**
 * Represents an [Argument] instance without a known property or getter source.
 *
 * These are only produced for raw `Argument` instances returned from
 * [Command.getCliCallComponents] calls.
 *
 * @param C Command class type.
 *
 * @param V Argument value type.
 */
internal class UnlinkedArgument<C : Any, V>(parent: ResolvedCommand<C>, instance: Argument<V>)
  : UnlinkedResolvedArgument<V>
  , AbstractUnlinkedArgument<V>(parent, instance)
{
  @Suppress("UNCHECKED_CAST")
  override val parentComponent
    get() = super.parentComponent as ResolvedCommand<C>
}
