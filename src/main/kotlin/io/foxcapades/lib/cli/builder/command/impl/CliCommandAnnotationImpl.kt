package io.foxcapades.lib.cli.builder.command.impl

import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation

@JvmInline
internal value class CliCommandAnnotationImpl(override val annotation: CliCommand) : CliCommandAnnotation {
  override val command
    get() = annotation.command

  @Suppress("UNCHECKED_CAST")
  override val positionalArgs
    get() = annotation.positionalArgs as Array<String>
}
