package io.foxcapades.lib.cli.builder.arg.ref.impl

import io.foxcapades.lib.cli.builder.arg.format.NullableGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.arg.ref.ResolvedArgument
import io.foxcapades.lib.cli.builder.component.ResolvedComponent
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.reflect.shouldQuote
import io.foxcapades.lib.cli.builder.util.values.ValueSource

// An argument that is just a value with no context
internal class HeadlessArgument<V>(
  parent: ResolvedComponent,
  value:  V,
  source: ValueSource,
)
  : ResolvedArgument<V>
{
  private val value = value

  override val parentComponent = parent

  override val valueSource = source

  override val shouldQuote by lazy { (value ?: return@lazy false)::class.shouldQuote() }

  override val isRequired
    get() = false

  override val hasDefault
    get() = false

  override val isSet
    get() = true

  override fun get() = value

  override fun set(value: V) = throw UnsupportedOperationException()

  override fun unset() = throw UnsupportedOperationException()

  override fun getDefault(): V =
    throw UnsupportedOperationException()

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) = true

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    NullableGeneralStringifier.unsafeCast<V>().formatValue(value, writer, this)
}
