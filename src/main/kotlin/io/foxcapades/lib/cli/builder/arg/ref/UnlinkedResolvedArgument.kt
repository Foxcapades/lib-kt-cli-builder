package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.component.ResolvedComponent

/**
 * An unlinked [Argument] is one that is only known to the CLI builder library
 * as a value with no known backing property or getter.
 *
 * These instances are primarily only used for `Argument` instances returned
 * from [Command.getCliCallComponents] calls.
 *
 * @param V Type of value wrapped by the resolved [Argument] instance.
 *
 * @since 1.0.0
 */
interface UnlinkedResolvedArgument<V> : ResolvedArgument<V> {
  override val parentComponent: ResolvedComponent
}
