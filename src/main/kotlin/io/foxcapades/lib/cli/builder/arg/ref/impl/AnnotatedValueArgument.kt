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
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal open class AnnotatedValueArgument<V>(
  annotation: CliArgumentAnnotation,
  parent:     ResolvedComponent,
  instance:   Argument<V>,
  source:     ValueSource,
)
  : UnlinkedResolvedArgument<V>
  , AbstractArgument<V>(parent, instance, source)
{
  private val annotation = annotation

  private val instance = instance

  override val parentComponent = parent

  override val valueSource = source

  override val isRequired
    get() = when (annotation.required) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> instance.isRequired
    }

  override val shouldQuote
    get() = when (annotation.shouldQuote) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> instance.shouldQuote
    }

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    if (annotation.hasFilter)
      annotation.filter.getOrCreate().unsafeCast<V>().shouldInclude(this, config, source)
    else
      instance.shouldSerialize(config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    if (annotation.hasFormatter)
      annotation.formatter.getOrCreate().unsafeCast<V>().formatValue(get(), writer, this)
    else
      super.writeToString(writer)
}
