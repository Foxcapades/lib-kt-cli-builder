package io.foxcapades.lib.cli.builder.flag.ref

import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.command.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.flag.Flag

sealed interface ResolvedFlag<V> : ResolvedComponent, Flag<ResolvedArgument<V>, V> {
  override val parentComponent: ResolvedCommand<*>
}

