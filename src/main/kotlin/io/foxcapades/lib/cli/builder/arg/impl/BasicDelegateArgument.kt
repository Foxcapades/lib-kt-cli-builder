package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.kt.prop.delegation.Property
import io.foxcapades.kt.prop.delegation.getOr
import io.foxcapades.kt.prop.delegation.getOrNull
import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.DelegateArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgUnsetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.*
import io.foxcapades.lib.cli.builder.util.reflect.property
import io.foxcapades.lib.cli.builder.util.values.ValueSource

internal class BasicDelegateArgument<V>(
  default:     Property<V>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<V>>,
  formatter:   Property<ArgumentFormatter<V>>,
)
  : AbstractDelegate<V>(
    if (default.isSet) 2 else 0,
    default.getOrNull(),
    null
  )
  , DelegateArgument<V>
{
  private val filter = filter.getOr(ArgUnsetFilter.unsafeCast())

  private val formatter = formatter.getOr(NonNullGeneralStringifier.unsafeCast())

  override val isRequired = isRequired.getOr(!default.isSet)

  override val shouldQuote = shouldQuote.getOr(false)

  override fun shouldSerialize(config: CliSerializationConfig, source: ValueSource) =
    filter.shouldInclude(this, config, source)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer, this)

  companion object {
    internal fun <V : Any> of(opts: ArgOptions<V>) = BasicDelegateArgument<V>(
      default     = ArgOptions<V>::default.property(opts),
      isRequired  = ArgOptions<V>::required.property(opts),
      shouldQuote = ArgOptions<V>::shouldQuote.property(opts),
      formatter   = ArgOptions<V>::formatter.property(opts),
      filter      = ArgOptions<V>::filter.property(opts),
    )
  }
}
