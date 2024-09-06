package io.foxcapades.lib.cli.wrapper.arg

import io.foxcapades.lib.cli.wrapper.Argument
import io.foxcapades.lib.cli.wrapper.serial.CliArgumentAppender
import io.foxcapades.lib.cli.wrapper.serial.NullableGeneralStringifier
import io.foxcapades.lib.cli.wrapper.serial.values.ArgSetFilter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentFormatter
import io.foxcapades.lib.cli.wrapper.serial.values.ArgumentPredicate
import io.foxcapades.lib.cli.wrapper.serial.values.unsafeCast
import io.foxcapades.lib.cli.wrapper.util.Property
import io.foxcapades.lib.cli.wrapper.util.getOr
import io.foxcapades.lib.cli.wrapper.util.getOrCompute
import io.foxcapades.lib.cli.wrapper.util.getOrNull

interface ComplexArgument<T> : Argument<T>

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
    filter      = filter.getOrNull() ?: ArgSetFilter.unsafeCast(),
  )
  , ComplexArgument<V>
{
  @Suppress("UNCHECKED_CAST")
  private val formatter = formatter.getOrCompute { NullableGeneralStringifier as ArgumentFormatter<V> }

  override fun writeToString(builder: CliArgumentAppender) =
    formatter.formatValue(get(), builder)
}
