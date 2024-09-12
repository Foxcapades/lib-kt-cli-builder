package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.Flag

interface ResolvedFlag<V> : ResolvedComponent, Flag<V> {
  override val parentComponent: ResolvedCommand<*>

  override val argument: ResolvedArgument<V>

  override val qualifiedName: String
    get() = "flag " + valueSource.reference
}

