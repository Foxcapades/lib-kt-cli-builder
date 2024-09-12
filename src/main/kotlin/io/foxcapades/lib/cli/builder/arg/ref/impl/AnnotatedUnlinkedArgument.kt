package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate

internal class AnnotatedUnlinkedArgument<V>(
  annotation: CliArgumentAnnotation,
  parent:     ResolvedComponent,
  instance:   Argument<V>,
  source:     ValueSource,
)
  : UnlinkedResolvedArgument<V>
  , UnlinkedArgument<V>(parent, instance, source)
{
  private val annotation = annotation

  override val isRequired: Boolean
    get() = when (annotation.required) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> super.isRequired
    }

  override val shouldQuote: Boolean
    get() = when (annotation.shouldQuote) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> super.shouldQuote
    }

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    if (annotation.hasFilter)
      annotation.initFilter().unsafeCast<V>().shouldInclude(this, config, source)
    else
      super.shouldSerialize(config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    if (annotation.hasFormatter)
      annotation.formatter.getOrCreate().unsafeCast<V>().formatValue(get(), writer, this)
    else
      super.writeToString(writer)
}
