package io.foxcapades.lib.cli.builder.command.ref

import io.foxcapades.lib.cli.builder.CliSerializationException
import io.foxcapades.lib.cli.builder.command.CliCommand
import io.foxcapades.lib.cli.builder.command.Command
import io.foxcapades.lib.cli.builder.command.impl.CliCommandAnnotationImpl
import io.foxcapades.lib.cli.builder.command.ref.impl.AnnotatedCommandInstance
import io.foxcapades.lib.cli.builder.command.ref.impl.CommandInstance
import io.foxcapades.lib.cli.builder.command.ref.impl.FauxCommand
import io.foxcapades.lib.cli.builder.util.reflect.findAnnotations
import io.foxcapades.lib.cli.builder.util.values.AnonymousComponentValue
import io.foxcapades.lib.cli.builder.util.values.ValueSource


internal fun <C : Any> ResolvedCommand(value: C, parent: ResolvedCommand<*>?, source: ValueSource?): ResolvedCommand<C> {
  @Suppress("UNCHECKED_CAST")
  if (value is ResolvedCommand<*>)
    return value as ResolvedCommand<C>

  val annotation = value::class.findAnnotations<CliCommand>()
    .firstOrNull()
    ?.second
    ?.let(::CliCommandAnnotationImpl)

  if (value is Command) {
    @Suppress("UNCHECKED_CAST")
    return (annotation?.let { AnnotatedCommandInstance(annotation, value, parent, source ?: AnonymousComponentValue) }
      ?: CommandInstance(value, parent, source ?: AnonymousComponentValue)) as ResolvedCommand<C>
  }

  if (annotation == null)
    throw CliSerializationException("CLI calls can only be built from Command instances or classes annotated with @CliCommand")

  return FauxCommand(annotation, value, parent, source ?: AnonymousComponentValue)
}
