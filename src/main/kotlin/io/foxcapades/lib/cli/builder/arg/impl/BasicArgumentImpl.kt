package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ArgOptions
import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NonNullGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.BasicMutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.util.properties.getOrNull
import io.foxcapades.lib.cli.builder.util.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.util.reflect.property
import kotlin.reflect.KCallable

internal class BasicArgumentImpl<V>(
  default:     Property<V>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  filter:      Property<ArgumentPredicate<Argument<V>, V>>,
  formatter:   Property<ArgumentFormatter<V>>,
)
  : BasicMutableDefaultableProperty<V>(
    if (default.isSet) 2 else 0,
    default.getOrNull(),
    null
  )
  , Argument<V>
{
  private val filter = filter.getOr(ArgSetFilter.unsafeCast())

  private val formatter = formatter.getOr(NonNullGeneralStringifier.unsafeCast())

  override val isRequired = isRequired.getOr(!default.isSet)

  override val shouldQuote = shouldQuote.getOr(false)

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, KCallable<V>>?,
  ) = filter.shouldInclude(this, config, reference)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer)

  companion object {
    internal fun <V : Any> of(opts: ArgOptions<V>) = BasicArgumentImpl<V>(
      default     = ArgOptions<V>::default.property(opts),
      isRequired  = ArgOptions<V>::required.property(opts),
      shouldQuote = ArgOptions<V>::shouldQuote.property(opts),
      formatter   = ArgOptions<V>::formatter.property(opts),
      filter      = ArgOptions<V>::filter.property(opts),
    )
  }
}
