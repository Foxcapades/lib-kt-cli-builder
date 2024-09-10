package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedDelegatedArgumentRef
import io.foxcapades.lib.cli.builder.command.ref.ResolvedCommand
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

internal class AnnotatedDelegateArgument<T : Any, V>(
  annotation: CliArgumentAnnotationImpl,
  parent:     ResolvedCommand<T>,
  delegate:   Argument<V>,
  accessor:   KCallable<V>,
)
  : ResolvedDelegatedArgumentRef<T, V>
  , DelegateArgument<T, V>(parent, delegate, accessor)
{
  private val annotation = annotation

  override val isRequired
    get() = when (annotation.required) {
      CliArgument.Toggle.Yes  -> true
      CliArgument.Toggle.No   -> false
      CliArgument.Toggle.Unset -> super.isRequired
    }

  override val shouldQuote
    get() = when (annotation.shouldQuote) {
      CliArgument.Toggle.Yes  -> true
      CliArgument.Toggle.No   -> false
      CliArgument.Toggle.Unset -> super.shouldQuote
    }

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = if (annotation.hasFilter)
    annotation.initFilter<V>().shouldInclude(this, config, reference)
  else
    super.shouldSerialize(config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    if (annotation.hasFormatter)
      annotation.initFormatter<V>().formatValue(get(), writer, this)
    else
      super.writeToString(writer)
}
