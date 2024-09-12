package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedDelegatedArgumentRef
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class AnnotatedDelegateArgument<T : Any, V>(
  annotation: CliArgumentAnnotationImpl,
  parent:     ResolvedCommand<T>,
  delegate:   Argument<V>,
  accessor:   ValueSource,
)
  : ResolvedDelegatedArgumentRef<T, V>
  , AbstractArgument<V>(parent, delegate, accessor)
{
  private val annotation = annotation

  @Suppress("UNCHECKED_CAST")
  override val parentComponent
    get() = super.parentComponent as ResolvedCommand<T>

  override val isRequired
    get() = when (annotation.required) {
      CliArgument.Toggle.Yes  -> true
      CliArgument.Toggle.No   -> false
      CliArgument.Toggle.Unset -> super.isRequired
    }

  override val shouldQuote
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
      annotation.initFormatter().unsafeCast<V>().formatValue(get(), writer, this)
    else
      super.writeToString(writer)
}
