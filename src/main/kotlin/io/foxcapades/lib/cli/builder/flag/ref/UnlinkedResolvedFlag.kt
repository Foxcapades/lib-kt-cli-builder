package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.flag.Flag
import io.foxcapades.lib.cli.builder.command.Command

/**
 * Represents a [Flag] instance for which the parent/container class is not
 * known.
 *
 * Such instances are typically created by returning a `Flag` instance from
 * [Command.getCliCallComponents].
 *
 * @param V Type of the underlying `Flag` instance's [Argument] value.
 *
 * @since 1.0.0
 */
interface UnlinkedResolvedFlag<V> : ResolvedFlag<V>
