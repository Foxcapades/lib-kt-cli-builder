package io.foxcapades.lib.cli.builder.command.ref.impl

import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.impl.ArbitraryComponentResolver
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class CommandInstance<C : Command>(
  instance: C,
  parent:   ResolvedCommand<*>?,
  source:   ValueSource,
)
  : ResolvedCommand<C>
{
  override val instance = instance
  override val parentComponent = parent
  override val valueSource = source

  override fun getCliCallComponents(config: CliSerializationConfig): Pair<String, Iterable<ResolvedComponent>> {
    val (com, it) = instance.getCliCallComponents(config)
    return com to Iterable { ArbitraryComponentResolver(config, this, it) }
  }
}
