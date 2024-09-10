package io.foxcapades.lib.cli.builder.arg.ref

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent

interface ResolvedArgument<V> : ResolvedComponent, Argument<V> {
  override val parentComponent: ResolvedComponent
}

