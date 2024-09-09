package io.foxcapades.lib.cli.builder.component

interface ResolvedComponent : CliCallComponent {
  val qualifiedName: String

  val parentComponent: ResolvedComponent?
}
