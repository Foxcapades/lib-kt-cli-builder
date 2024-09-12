package io.foxcapades.lib.cli.builder.command

import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation

interface CliCommandAnnotation : CliComponentAnnotation<CliCommand> {
  val command: String
  val positionalArgs: Array<String>
}

