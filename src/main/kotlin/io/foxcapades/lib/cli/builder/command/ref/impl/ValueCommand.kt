package io.foxcapades.lib.cli.builder.command.ref.impl

import io.foxcapades.lib.cli.builder.command.CliCommandComponents
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommandValueRef
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import kotlin.reflect.KCallable

internal class ValueCommand<P : Any, C : Any>(
  instance: C,
  parent:   ResolvedCommand<P>,
  accessor: KCallable<C>
)
  : ResolvedCommandValueRef<P, C>
{
  override val instance = instance

  override val parentComponent = parent

  override val containingType
    get() = parentComponent.type

  override val accessor = accessor

  override fun getCliCallComponents(config: CliSerializationConfig): CliCommandComponents {

    TODO("Not yet implemented")
  }
}
