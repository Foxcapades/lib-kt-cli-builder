package io.foxcapades.lib.cli.builder.arg.impl

import io.foxcapades.lib.cli.builder.arg.ComplexArgument
import io.foxcapades.lib.cli.builder.arg.filter.ArgSetFilter
import io.foxcapades.lib.cli.builder.arg.filter.ArgumentPredicate
import io.foxcapades.lib.cli.builder.arg.filter.unsafeCast
import io.foxcapades.lib.cli.builder.arg.format.ArgumentFormatter
import io.foxcapades.lib.cli.builder.arg.format.NullableGeneralStringifier
import io.foxcapades.lib.cli.builder.arg.format.unsafeCast
import io.foxcapades.lib.cli.builder.serial.CliArgumentWriter
import io.foxcapades.lib.cli.builder.util.properties.Property
import io.foxcapades.lib.cli.builder.util.properties.getOr

internal open class ComplexArgumentImpl<out A : ComplexArgument<V>, V>(
  default:     Property<V>,
  isRequired:  Property<Boolean>,
  shouldQuote: Property<Boolean>,
  formatter:   Property<ArgumentFormatter<V>>,
  filter:      Property<ArgumentPredicate<A, V>>,
)
  : AbstractArgument<A, V>(
    default     = default,
    isRequired  = isRequired.getOr(!default.isSet),
    shouldQuote = shouldQuote.getOr(true),
    filter      = filter.getOr(ArgSetFilter.unsafeCast()),
  )
  , ComplexArgument<V>
{
  private val formatter = formatter.getOr(NullableGeneralStringifier.unsafeCast())

  override fun writeToString(writer: CliArgumentWriter<*, V>) =
    formatter.formatValue(get(), writer)
}
