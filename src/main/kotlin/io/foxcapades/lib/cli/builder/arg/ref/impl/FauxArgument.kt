package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.CliArgumentAnnotation
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.NoSuchDefaultValueException
import io.foxcapades.lib.cli.builder.util.reflect.shouldQuote
import io.foxcapades.lib.cli.builder.util.values.ValueAccessor
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class FauxArgument<V>(
  annotation: CliArgumentAnnotation,
  parent:     ResolvedComponent,
  accessor:   ValueAccessor<V>,
)
  : ResolvedArgument<V>
{
  private val annotation = annotation

  override val parentComponent = parent

  override val valueSource = accessor

  // TODO: this should be smarter
  override val isRequired
    get() = annotation.required == CliArgument.Toggle.Yes

  override val shouldQuote = when (annotation.shouldQuote) {
    CliArgument.Toggle.Yes   -> true
    CliArgument.Toggle.No    -> false
    CliArgument.Toggle.Unset -> get()?.let{ it::class }?.shouldQuote() ?: false
  }

  override val hasDefault
    get() = false

  override val isSet: Boolean
    get() = true

  override fun get() =
    valueSource()

  override fun getDefault() =
    throw NoSuchDefaultValueException()

  override fun set(value: V) =
    throw UnsupportedOperationException()

  override fun unset() =
    throw UnsupportedOperationException()

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    annotation.initFilter().unsafeCast<V>().shouldInclude(this, config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    annotation.initFormatter().unsafeCast<V>().formatValue(get(), writer, this)
}
