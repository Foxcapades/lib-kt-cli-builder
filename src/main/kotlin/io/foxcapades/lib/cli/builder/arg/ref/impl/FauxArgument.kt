package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.CliArgument
import io.foxcapades.lib.cli.builder.arg.impl.CliArgumentAnnotationImpl
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedImaginaryArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.NoSuchDefaultValueException
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import kotlin.reflect.KCallable

internal class FauxArgument<T : Any, V>(
  annotation: CliArgumentAnnotationImpl,
  container:  T,
  parent:     ResolvedComponent,
  accessor:   KCallable<V>,
)
  : ResolvedImaginaryArgument<T, V>
{
  private val container = container

  override val annotation = annotation

  override val containingType
    get() = container::class

  override val parentComponent = parent

  override val accessor = accessor

  // TODO: this should be smarter
  override val isRequired
    get() = annotation.required == CliArgument.Toggle.Yes

  // TODO: this should be smarter
  override val shouldQuote
    get() = annotation.shouldQuote == CliArgument.Toggle.Yes

  override val hasDefault
    get() = false

  override val isSet: Boolean
    get() = true

  override fun get() = accessor.call(container)

  override fun getDefault() = throw NoSuchDefaultValueException()

  override fun set(value: V) {
    throw UnsupportedOperationException()
  }

  override fun unset() {
    throw UnsupportedOperationException()
  }

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = annotation.initFilter<V>().shouldInclude(this, config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    annotation.initFormatter<V>().formatValue(get(), writer, this)
}
