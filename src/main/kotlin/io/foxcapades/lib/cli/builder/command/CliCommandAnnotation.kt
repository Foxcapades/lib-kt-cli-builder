package io.foxcapades.lib.cli.builder.command

import io.foxcapades.lib.cli.builder.component.CliComponentAnnotation

@JvmInline
internal value class CliCommandAnnotation(val annotation: CliCommand) : CliComponentAnnotation {
  inline val command
    get() = annotation.command

  inline val extras
    get() = annotation.extras
}

