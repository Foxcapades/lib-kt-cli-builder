package io.foxcapades.lib.cli.builder.command.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.HeadlessArgument
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.impl.ReflectiveComponentResolver
import io.foxcapades.lib.cli.builder.util.values.AnonymousComponentValue
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class FauxCommand<C : Any>(
  annotation: CliCommandAnnotation,
  instance:   C,
  parent:     ResolvedCommand<*>?,
  source:     ValueSource,
)
  : ResolvedCommand<C>
{
  private val annotation = annotation

  override val instance = instance
  override val parentComponent = parent
  override val valueSource = source

  override fun getCliCallComponents(config: CliSerializationConfig): Pair<String, Iterable<ResolvedComponent>> {
    return annotation.command to Iterable { iterator {
      // return the values from the command annotation first
      for (arg in annotation.positionalArgs)
        yield(HeadlessArgument(this@FauxCommand, arg, AnonymousComponentValue))

      // then the components that could be found on the instance class
      for (component in ReflectiveComponentResolver(this@FauxCommand, instance, config))
        yield(component)
    } }
  }
}
