package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.Argument
import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.reflect.ValueAccessorReference
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.serial.CliSerializationConfig
import io.foxcapades.lib.cli.builder.util.properties.BasicMutableDefaultableProperty
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr
import io.foxcapades.lib.cli.builder.util.properties.getOrNull
import kotlin.reflect.KCallable

internal abstract class AbstractArgument<out A : Argument<V>, V>(
  default:     Property<V>,
  isRequired:  Property<Boolean>,
  shouldQuote: Boolean,
  filter:      Property<ArgumentPredicate<A, V>>,
  formatter:   ArgumentFormatter<V>,
)
  : BasicMutableDefaultableProperty<V>(
    if (default.isSet) 2 else 0,
    default.getOrNull(),
    null
  )
  , Argument<V>
{
  private val filter = filter.getOr(ArgSetFilter.unsafeCast())

  private val formatter = formatter

  @Suppress("UNCHECKED_CAST")
  protected inline val self get() = this as A

  override val isRequired = isRequired.getOr(!default.isSet)

  override val shouldQuote = shouldQuote

  override fun shouldSerialize(
    config:    CliSerializationConfig,
    reference: ValueAccessorReference<*, V, out KCallable<V>>,
  ) = filter.shouldInclude(self, reference, config)

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer)
}
