package io.foxcapades.lib.cli.builder.command.ref.impl

import io.foxcapades.lib.cli.builder.arg.ref.impl.HeadlessArgument
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.CliCommandAnnotation
import io.foxcapades.lib.cli.builder.command.CliCommandComponents
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.serial.impl.ArbitraryComponentResolver
import io.foxcapades.lib.cli.builder.util.values.AnonymousComponentValue
import io.foxcapades.lib.cli.builder.util.values.ValueSource

/**
 * For cases when a class both implements [Command] and is annotated with
 * [CliCommand].
 *
 * In this situation, the annotation values are returned first, then the values
 * from the implementation's [getCliCallComponents] method.
 *
 * The command string returned as the first value in the [CliCommandComponents]
 * pair returned by the implementer's [getCliCallComponents] is disregarded.
 */
internal class AnnotatedCommandInstance<C : Command>(
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
        yield(HeadlessArgument(this@AnnotatedCommandInstance, arg, AnonymousComponentValue))

      val (_, it) = instance.getCliCallComponents(config)

      // then the components that could be found on the instance class
      for (component in ArbitraryComponentResolver(config, this@AnnotatedCommandInstance, it))
        yield(component)
    } }
  }
}
