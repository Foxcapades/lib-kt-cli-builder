package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.UnlinkedResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.getOrCreate
import kotlin.reflect.KCallable

internal abstract class BaseAnnotatedValueArgument<P : ResolvedComponent, V>(
  annotation: CliArgumentAnnotationImpl,
  parent:     P,
  instance:   Argument<V>
)
  : UnlinkedResolvedArgument<V>
  , AbstractUnlinkedArgument<V>(parent, instance)
{
  private val annotation = annotation

  @Suppress("UNCHECKED_CAST")
  override val parentComponent: P
    get() = super.parentComponent as P

  override val isRequired
    get() = when (annotation.required) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> super.isRequired
    }

  override val shouldQuote
    get() = when (annotation.shouldQuote) {
      CliArgument.Toggle.Yes   -> true
      CliArgument.Toggle.No    -> false
      CliArgument.Toggle.Unset -> super.shouldQuote
    }

  override fun shouldSerialize(
    config: CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?
  ) = if (annotation.hasFilter)
    annotation.filter.getOrCreate().unsafeCast<Argument<V>, V>().shouldInclude(this, config, reference)
  else
    super.shouldSerialize(config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    if (annotation.hasFormatter)
      annotation.formatter.getOrCreate().unsafeCast<V>().formatValue(get(), writer, this)
    else
      super.writeToString(writer)
}
