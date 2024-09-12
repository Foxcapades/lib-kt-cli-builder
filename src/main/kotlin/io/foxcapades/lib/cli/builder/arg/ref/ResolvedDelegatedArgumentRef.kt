package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand

interface ResolvedDelegatedArgumentRef<T : Any, V> : ResolvedArgument<V> {
  override val parentComponent: ResolvedCommand<T>
}
