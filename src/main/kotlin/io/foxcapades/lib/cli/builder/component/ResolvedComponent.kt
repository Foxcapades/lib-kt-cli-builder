package io.foxcapades.lib.cli.builder.component

import io.foxcapades.lib.cli.builder.util.values.ValueSource

interface ResolvedComponent : CliCallComponent {
  val qualifiedName: String

  val parentComponent: ResolvedComponent?

  val valueSource: ValueSource
}
